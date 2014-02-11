package com.eugenefe.feed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.eugenefe.entity.component.PriceData;
import com.eugenefe.entity.component.QpriceData;
//import com.eugenefe.entity.QFutures;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.HibernateUtil;

//@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name = "QDATA_MASTER")
public class QDataset {
    private String id;
	private String name;
    private String code;
    private String source_code;
    private String type ;
    
    private String display_url;
    private String urlize_name;
	private String frequency;
    private String from_date;
    private String to_date;
    
//    @JsonIgnore
//    private boolean _private;
    @JsonIgnore
    private String description;
    private String updated_at;
    
//    @JsonIgnore
//    public List<String> errors;
//    @JsonIgnore
//    public String error;

    private Map<String, QpriceData> priceMap = new HashMap<String, QpriceData>();
    
    private List<String> column_names = new ArrayList<String>();
    private List<List<String>> data = new ArrayList<List<String>>();

    public QDataset() {
    }


	@Id
	@Column(name = "Q_ID", nullable = false, length = 20)
	@AnnoMethodTree(order =10, init=true)
	public String getId() {return id;}
	public void setId(String id) {this.id = id;	}
	
	@Column(name = "Q_NAME")
	@AnnoMethodTree(order =11, init=true)
	public String getName() {return name;}
	public void setName(String name) {this.name = name;	}
	
	@Column(name = "Q_SOURCE", nullable = false)
	@AnnoMethodTree(order =20, init=true)
	public String getSource_code() {return source_code;	}
	public void setSource_code(String source_code) {this.source_code = source_code;	}

	@Column(name = "Q_CODE", nullable = false)
	@AnnoMethodTree(order =21, init=true)
	public String getCode() {	return code;	}
	public void setCode(String code) {		this.code = code;	}
	
	@Column(name = "Q_TYPE")
	@AnnoMethodTree(order =22, init=true)
	public String getType() {		return type;	}
	public void setType(String type) {		this.type = type;	}	

	@Column(name = "URLIZE_NAME", nullable = false)
	@AnnoMethodTree(order =30, init=true)
	public String getUrlize_name() {		return urlize_name;	}
	public void setUrlize_name(String urlize_name) {this.urlize_name = urlize_name;	}

	@Column(name = "FREQUENCY")
	@AnnoMethodTree(order =40, init=true)
	public String getFrequency() {	return frequency;}
	public void setFrequency(String frequency) {	this.frequency = frequency;	}

	@Column(name = "FROM_DATE")
	@AnnoMethodTree(order =50, init=true)
	public String getFrom_date() {	return from_date;}
	public void setFrom_date(String from_date) {this.from_date = from_date;}
	
	@Column(name = "TO_DATE")
	@AnnoMethodTree(order =51, init=true)
	public String getTo_date() {return to_date;}
	public void setTo_date(String to_date) {this.to_date = to_date;	}


	@Column(name = "DISPLAY_URL")
	@AnnoMethodTree(order =60, init=true)
	public String getDisplay_url() {return display_url;	}
	public void setDisplay_url(String display_url) {this.display_url = display_url;}
	
//	@Transient
//	@Column(name = "PRIVATE_YN")
//	@AnnoMethodTree(order =70, init=true)
//	public boolean isPrivate() {return _private;}
//	public void setPrivate(boolean _private) {	this._private = _private;	}

	@Column(name="DESCRIPTION")
	@AnnoMethodTree(order =90, init=true)
	public String getDescription() {return description;	}
	public void setDescription(String description) {this.description = description;	}
	
	@Column(name = "UPDATE_DATE")
	@AnnoMethodTree(order =95, init=true)
	public String getUpdated_at() {	return updated_at;}
	public void setUpdated_at(String updated_at) {this.updated_at = updated_at;	}

	
	@ElementCollection
	@CollectionTable(name="QDATA_HIS", joinColumns= @JoinColumn(name="Q_ID"))
	@MapKeyColumn(name="BASE_DATE")	
//	@AttributeOverrides({
//		@AttributeOverride(name="basePrice",column = @Column(name = "CLOSE_PRICE"))
//		,@AttributeOverride(name="openPrice",column = @Column(name = "CLOSE_PRICE"))
//	})
	public Map<String, QpriceData> getPriceMap() {
		return priceMap;
	}
	public void setPriceMap(Map<String, QpriceData> priceMap) {
		this.priceMap = priceMap;
	}


	@Transient
	public List<List<String>> getData() {
		return data;
	}
	public void setData(List<List<String>> data) {
		this.data = data;
	}
	
	@Transient
	public List<String> getColumn_names() {
		return column_names;
	}
	public void setColumn_names(List<String> column_names) {
		this.column_names = column_names;
	}
}
