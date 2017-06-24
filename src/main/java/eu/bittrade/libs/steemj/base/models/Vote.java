package eu.bittrade.libs.steemj.base.models;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Vote {
    /* TODO:
     * 
   struct account_vote
   {
      string         authorperm;
      uint64_t       weight = 0;
      int64_t        rshares = 0;
      int16_t        percent = 0;
      time_point_sec time;
   };
     */
    private String authorperm;
    private String weight;
    private String rshares;
    private int percent;
    private Date time;

    public String getAuthorperm() {
        return authorperm;
    }

    public String getWeight() {
        return weight;
    }

    public String getRshares() {
        return rshares;
    }

    public int getPercent() {
        return percent;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
