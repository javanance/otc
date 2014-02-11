package com.eugenefe.feed;

public enum EQuandlParam {
		ID("auth_token")
		, PAGE_NO("page")
		, ROWS("rows")
		, START_DATE("trim_start")
		, END_DATE("trim_end")
		, SORT("sort_order")
		, QUERY("query")
		, Columns("columns");
		
		private String param;

		private EQuandlParam(String param) {
			this.param = param;
		}

		public String getParamString() {
			return this.param;
		}
}
