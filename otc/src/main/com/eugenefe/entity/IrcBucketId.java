package com.eugenefe.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
@Embeddable
@AnnoNavigationFilter
public class IrcBucketId implements java.io.Serializable {

		private String ircId;
		private String maturityId;

		public IrcBucketId() {
		}

		public IrcBucketId(String ircId, String maturityId) {
			this.ircId = ircId;
			this.maturityId = maturityId;
		}

		@Column(name = "IRC_ID", nullable = false)
		@NotNull
		@AnnoMethodTree(order=10, init=true)
		public String getIrcId() {
			return this.ircId;
		}

		public void setIrcId(String ircId) {
			this.ircId = ircId;
		}

		@Column(name = "MATURITY_ID", nullable = false, length = 20)
		@NotNull
		@Size(max = 20)
		@AnnoMethodTree(order=20, init=true)
		public String getMaturityId() {
			return this.maturityId;
		}

		public void setMaturityId(String maturityId) {
			this.maturityId = maturityId;
		}

		public boolean equals(Object other) {
			if ((this == other))
				return true;
			if ((other == null))
				return false;
			if (!(other instanceof IrcBucketId))
				return false;
			IrcBucketId castOther = (IrcBucketId) other;

			return ((this.getIrcId() == castOther.getIrcId()) || (this.getIrcId() != null && castOther.getIrcId() != null && this
					.getIrcId().equals(castOther.getIrcId())))
					&& ((this.getMaturityId() == castOther.getMaturityId()) || (this.getMaturityId() != null && castOther.getMaturityId() != null && this
							.getMaturityId().equals(castOther.getMaturityId())));
		}

		public int hashCode() {
			int result = 17;

			result = 37 * result + (getIrcId() == null ? 0 : this.getIrcId().hashCode());
			result = 37 * result + (getMaturityId() == null ? 0 : this.getMaturityId().hashCode());
			return result;
		}

}
