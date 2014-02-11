package com.eugenefe.feed;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.eugenefe.mvfeed.others.GeocodeResponse;
//import com.eugenefe.mvfeed.others.Place;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;

public class QConnection {

	private String token;
	private boolean isToken = false;
	private final String baseUrl = "http://www.quandl.com/api/v1/datasets/";
	private final String collectionUrl = "http://www.quandl.com/api/v1/current_user/collections/datasets/favourites.json?auth_token=";
	private final String queryUrl = "http://www.quandl.com/api/v1/datasets.json?";
	private final String multiSetUrl = "http://quandl.com/api/v1/multisets.json?";

	

	public QConnection() {
	}

	public QDataset getQDataset( String authToken, String qCode ) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(EQuandlParam.ID.getParamString(), authToken);
		return getQDataset(qCode, params);
	}

	public QDataset getQDataset(String authToken, String qCode,  int pageNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(EQuandlParam.ID.getParamString(), authToken);
		params.put(EQuandlParam.PAGE_NO.getParamString(),String.valueOf(pageNo));
		return getQDataset(qCode, params);
	}

	public QDataset getQDataset(String authToken,String qCode, String startDate, String endDate) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(EQuandlParam.ID.getParamString(), authToken);
		params.put(EQuandlParam.START_DATE.getParamString(), startDate);
		params.put(EQuandlParam.END_DATE.getParamString(), endDate);
		return getQDataset(qCode, params);
	}

	public QDataset getQDataset(String qCode, Map<String, String> params) {
		HttpClient httpclient = new DefaultHttpClient();
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			URIBuilder builder = new URIBuilder(baseUrl + qCode + ".json");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.setParameter(entry.getKey(), entry.getValue());
			}
			URI uri = builder.build();
			HttpGet request = new HttpGet(uri);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
//				System.out.println("EntityUtil:" + EntityUtils.toString(entity));
				QDataset rst = mapper.readValue(EntityUtils.toString(entity),
						QDataset.class);
				return rst;
			} else {
				return null;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	
	public QDatasetList getCodeQuery(Map<String, String> params) {
		HttpClient httpclient = new DefaultHttpClient();
		ObjectMapper mapper = new ObjectMapper();
		QDatasetList rst = new QDatasetList();

		try {
			URIBuilder builder = new URIBuilder(queryUrl);
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.setParameter(entry.getKey(), entry.getValue());
			}
			URI uri = builder.build();
			HttpGet request = new HttpGet(uri);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				rst = mapper.readValue(EntityUtils.toString(entity),QDatasetList.class);
				return rst;
			} else {
				return null;
			}

		}  catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	public QDatasetList getCodeQuery(String authToken, String query) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(EQuandlParam.ID.getParamString(), authToken);
		params.put(EQuandlParam.QUERY.getParamString(), query);
		return getCodeQuery(params);
	}

	public QDatasetList getCodeQuery(String authToken, String query,int pageNo) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(EQuandlParam.ID.getParamString(), authToken);
		params.put(EQuandlParam.QUERY.getParamString(), query);
		params.put(EQuandlParam.PAGE_NO.getParamString(), String.valueOf(pageNo));
		return getCodeQuery(params);
	}
	
	public QDatasetList getCodeQuery(String query, int pageNo, String sDate, String eDate) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(EQuandlParam.QUERY.getParamString(), query);
		params.put(EQuandlParam.PAGE_NO.getParamString(), String.valueOf(pageNo));
		params.put(EQuandlParam.START_DATE.getParamString(), sDate);
		params.put(EQuandlParam.END_DATE.getParamString(), eDate);
		return getCodeQuery(params);
	}
	
	public QMultiset getMultiData(String authToken, String multiset, int pageNo, String sDate, String eDate) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(EQuandlParam.ID.getParamString(), authToken);
		params.put(EQuandlParam.Columns.getParamString(), multiset);
		params.put(EQuandlParam.PAGE_NO.getParamString(), String.valueOf(pageNo));
		params.put(EQuandlParam.START_DATE.getParamString(), sDate);
		params.put(EQuandlParam.END_DATE.getParamString(), eDate);
		return getMultiData(params);
	}
	
	public QMultiset getMultiData(String authToken, String multiset, int pageNo) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(EQuandlParam.ID.getParamString(), authToken);
		params.put(EQuandlParam.Columns.getParamString(), multiset);
		params.put(EQuandlParam.PAGE_NO.getParamString(), String.valueOf(pageNo));
		return getMultiData(params);
	}

	public QMultiset getMultiData(String authToken, String multiset) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(EQuandlParam.ID.getParamString(), authToken);
		params.put(EQuandlParam.Columns.getParamString(), multiset);
		return getMultiData(params);
	}

	
	public QMultiset getMultiData(Map<String, String> params) {
		HttpClient httpclient = new DefaultHttpClient();
		ObjectMapper mapper = new ObjectMapper();
		QMultiset rst = new QMultiset();

		try {
			URIBuilder builder = new URIBuilder(multiSetUrl);
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.setParameter(entry.getKey(), entry.getValue());
			}
			URI uri = builder.build();
			HttpGet request = new HttpGet(uri);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				rst = mapper.readValue(EntityUtils.toString(entity),QMultiset.class);
				return rst;
			} else {
				return null;
			}

		}  catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	

	
	
	/*public QMultiset getMultiData(String multiset) {
		String url;
		if (isToken) {
			url = multiSetUrl + token + "&columns=" + multiset;
			return requestQMultiset(url);
		}
		return null;
	}

	public QMultiset getMultiDataBetweenDates(String multiset, String start,
			String end) {
		String url;
		if (isToken) {
			url = multiSetUrl + token + "&columns=" + multiset + "&trim_start="
					+ start + "&trim_end=" + end;
			return requestQMultiset(url);
		}
		return null;

	}*/
}