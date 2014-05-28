package com.eugenefe.controller.temp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.entity.SyntheticDetail;
import com.eugenefe.entity.Synthetics;
import com.eugenefe.entity.crud.IntrinsicBond;
import com.eugenefe.entity.crud.OptionCrud;
import com.eugenefe.entity.raws.Pdif004m0;
import com.eugenefe.enums.raw.EElsOptionType;

@Name("elsOptionInit")
@Scope(ScopeType.CONVERSATION)
// @AutoCreate
public class ElsOptionInit {
	@Logger
	private Log log;
	@In
	private Session session;
	// @In private ComboListInit comboListInit;

	private List<OptionCrud> optionCrudList = new ArrayList<OptionCrud>();
	private List<IntrinsicBond> intrBondList = new ArrayList<IntrinsicBond>();
	private List<Synthetics> synList = new ArrayList<Synthetics>();
	private List<SyntheticDetail> synDetailList = new ArrayList<SyntheticDetail>();
	List<String> selIdList = new ArrayList<String>();

	private List<Pdif004m0> elsMasterList = new ArrayList<Pdif004m0>();

	private List<SyntheticDetail> selectSynDetailList = new ArrayList<SyntheticDetail>();
	private List<OptionCrud> selectOptionCrudList = new ArrayList<OptionCrud>();
	private List<IntrinsicBond> selectIntrBondList = new ArrayList<IntrinsicBond>();
	private SyntheticDetail selectSynDetail;

	private TreeNode rootNode;
	private TreeNode selectNode;

	public ElsOptionInit() {
		System.out.println("Construction ElsOptionInit");
	}

	// ************************Getter and Setter********************

	public List<OptionCrud> getOptionCrudList() {
		return optionCrudList;
	}

	public List<Pdif004m0> getElsMasterList() {
		return elsMasterList;
	}

	public void setElsMasterList(List<Pdif004m0> elsMasterList) {
		this.elsMasterList = elsMasterList;
	}

	public List<OptionCrud> getSelectOptionCrudList() {
		return selectOptionCrudList;
	}

	public void setSelectOptionCrudList(List<OptionCrud> selectOptionCrudList) {
		this.selectOptionCrudList = selectOptionCrudList;
	}

	public List<IntrinsicBond> getSelectIntrBondList() {
		return selectIntrBondList;
	}

	public void setSelectIntrBondList(List<IntrinsicBond> selectIntrBondList) {
		this.selectIntrBondList = selectIntrBondList;
	}

	public SyntheticDetail getSelectSynDetail() {
		return selectSynDetail;
	}

	public void setSelectSynDetail(SyntheticDetail selectSynDetail) {
		this.selectSynDetail = selectSynDetail;
	}

	public void setOptionCrudList(List<OptionCrud> optionCrudList) {
		this.optionCrudList = optionCrudList;
	}

	public List<IntrinsicBond> getIntrBondList() {
		return intrBondList;
	}

	public void setIntrBondList(List<IntrinsicBond> intrBondList) {
		this.intrBondList = intrBondList;
	}

	public List<Synthetics> getSynList() {
		return synList;
	}

	public void setSynList(List<Synthetics> synList) {
		this.synList = synList;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public TreeNode getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(TreeNode selectNode) {
		this.selectNode = selectNode;
	}

	public List<SyntheticDetail> getSelectSynDetailList() {
		return selectSynDetailList;
	}

	public void setSelectSynDetailList(List<SyntheticDetail> selectSynDetailList) {
		this.selectSynDetailList = selectSynDetailList;
	}

	public List<SyntheticDetail> getSynDetailList() {
		return synDetailList;
	}

	public void setSynDetailList(List<SyntheticDetail> synDetailList) {
		this.synDetailList = synDetailList;
	}

	// *****************************************************
	@Create
	public void create() {
		setTree();
		optionCrudList = session.createCriteria(OptionCrud.class).list();
		synList = session.createCriteria(Synthetics.class).list();
		synDetailList = session.createCriteria(SyntheticDetail.class).list();
		elsMasterList = session.createCriteria(Pdif004m0.class).list();

//		selectSynDetailList = synDetailList;
//		selectOptionCrudList = optionCrudList;

	}

	private void setTree() {
		rootNode = new DefaultTreeNode("ROOT", null);
		TreeNode koNode = new DefaultTreeNode("up", "KnockOut", rootNode);
		TreeNode spNode = new DefaultTreeNode("up", "Spread", rootNode);
		TreeNode pvNode = new DefaultTreeNode("up", "PlainVanilla", rootNode);
		TreeNode dgNode = new DefaultTreeNode("up", "Digital", rootNode);
		// TreeNode sdNode = new DefaultTreeNode("StepDown", rootNode);
		// TreeNode raNode = new DefaultTreeNode("SD", rootNode);

		for (EElsOptionType aa : EElsOptionType.values()) {
			if (aa.getOptGroup().equals("KO")) {
				TreeNode temp = new DefaultTreeNode("down", aa, koNode);
			} else if (aa.getOptGroup().equals("SP")) {
				TreeNode temp = new DefaultTreeNode("down", aa, spNode);
			} else if (aa.getOptGroup().equals("PV")) {
				TreeNode temp = new DefaultTreeNode("down", aa, pvNode);
			} else if (aa.getOptGroup().equals("DG")) {
				TreeNode temp = new DefaultTreeNode("down", aa, dgNode);
			}
		}
	}

	public void nodeSelect(NodeSelectEvent event) {
		log.info("node :#0", selectNode.getData().toString());

		if (selectNode.getData() instanceof String) {
			return;
		} else {
			EElsOptionType temp = (EElsOptionType)selectNode.getData();
			selIdList.clear();
			selectSynDetailList.clear();
			selectOptionCrudList.clear();

			for (Pdif004m0 aa : elsMasterList) {
				if (temp.getIntCode().equals(aa.getElsOptDvsnCd())) {
					selIdList.add(aa.getPdno());
				}
			}

			for (SyntheticDetail aa : synDetailList) {
				if (selIdList.contains(aa.getId().getSynProdId())) {
					selectSynDetailList.add(aa);
				}
			}

			for (OptionCrud aa : optionCrudList) {
				for (SyntheticDetail bb : selectSynDetailList) {
					if (bb.getId().getSubProdId().equals(aa.getOptionId())) {
						selectOptionCrudList.add(aa);
					}
				}
			}
		}
	}

}
