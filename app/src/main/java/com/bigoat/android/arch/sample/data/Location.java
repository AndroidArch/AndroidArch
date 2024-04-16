package com.bigoat.android.arch.sample.data;

/**
 * {
 *       "name":"北京",
 *       "id":"101010100",
 *       "lat":"39.90499",
 *       "lon":"116.40529",
 *       "adm2":"北京",
 *       "adm1":"北京市",
 *       "country":"中国",
 *       "tz":"Asia/Shanghai",
 *       "utcOffset":"+08:00",
 *       "isDst":"0",
 *       "type":"city",
 *       "rank":"10",
 *       "fxLink":"https://www.qweather.com/weather/beijing-101010100.html"
 *     }
 */
public class Location implements java.io.Serializable{
    public String name;
    public String id;
    public String lat;
    public String lon;
    public String adm2;
    public String adm1;
    public String country;
    public String tz;
    public String utcOffset;
    public String isDst;
    public String type;
    public String rank;
    public String fxLink;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Location{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", lat='").append(lat).append('\'');
        sb.append(", lon='").append(lon).append('\'');
        sb.append(", adm2='").append(adm2).append('\'');
        sb.append(", adm1='").append(adm1).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", tz='").append(tz).append('\'');
        sb.append(", utcOffset='").append(utcOffset).append('\'');
        sb.append(", isDst='").append(isDst).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", rank='").append(rank).append('\'');
        sb.append(", fxLink='").append(fxLink).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
