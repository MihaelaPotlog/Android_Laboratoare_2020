package Service;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class SiteModel extends RealmObject {
    @Required
    @PrimaryKey
    private String Id;
    @Required
    private String Url;
    private Date  LastModifiedDate;
    private String Hash;
    private String Name;
    private boolean Modified;



    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public void setModified(boolean modified) {
        Modified = modified;
    }

    public boolean isModified() {
        return Modified;
    }

    public Date getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModified) {
        LastModifiedDate = lastModified;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }
        public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }


}
