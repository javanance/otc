package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("vcvMatrixList")
public class VcvMatrixList extends EntityQuery<VcvMatrix> {

	private static final String EJBQL = "select vcvMatrix from VcvMatrix vcvMatrix";

	private static final String[] RESTRICTIONS = {
			"lower(vcvMatrix.vcvId) like lower(concat(#{vcvMatrixList.vcvMatrix.vcvId},'%'))",
			 };

	private VcvMatrix vcvMatrix = new VcvMatrix();

	public VcvMatrixList() {
		setEjbql(EJBQL);
//		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
//		setMaxResults(25);
	}

	public VcvMatrix getVcvMatrix() {
		return vcvMatrix;
	}
}
