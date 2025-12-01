package domain;

/**
 * ReferralEntity for patient referrals.
 * @author Vishal Hirani
 */
public class ReferralEntity {
    private String refId;
    private String ptId;
    private String fromClinId;
    private String toClinId;
    private String fromFacId;
    private String toFacId;
    private String refDate;
    private String urgLvl;
    private String refReason;
    private String clinSum;
    private String reqTests;
    private String refStatus;
    private String apptId;
    private String refNotes;
    private String createDate;
    private String updateDate;
    
    public ReferralEntity() {
        this.refId = "";
        this.ptId = "";
        this.fromClinId = "";
        this.toClinId = "";
        this.fromFacId = "";
        this.toFacId = "";
        this.refDate = "";
        this.urgLvl = "Routine";
        this.refReason = "";
        this.clinSum = "";
        this.reqTests = "";
        this.refStatus = "New";
        this.apptId = "";
        this.refNotes = "";
        this.createDate = "";
        this.updateDate = "";
    }
    
    // Getters
    public String getRefId() { return refId; }
    public String getPtId() { return ptId; }
    public String getFromClinId() { return fromClinId; }
    public String getToClinId() { return toClinId; }
    public String getFromFacId() { return fromFacId; }
    public String getToFacId() { return toFacId; }
    public String getRefDate() { return refDate; }
    public String getUrgLvl() { return urgLvl; }
    public String getRefReason() { return refReason; }
    public String getClinSum() { return clinSum; }
    public String getReqTests() { return reqTests; }
    public String getRefStatus() { return refStatus; }
    public String getApptId() { return apptId; }
    public String getRefNotes() { return refNotes; }
    public String getCreateDate() { return createDate; }
    public String getUpdateDate() { return updateDate; }
    
    // Setters
    public void setRefId(String id) { this.refId = id; }
    public void setPtId(String id) { this.ptId = id; }
    public void setFromClinId(String id) { this.fromClinId = id; }
    public void setToClinId(String id) { this.toClinId = id; }
    public void setFromFacId(String id) { this.fromFacId = id; }
    public void setToFacId(String id) { this.toFacId = id; }
    public void setRefDate(String date) { this.refDate = date; }
    public void setUrgLvl(String lvl) { this.urgLvl = lvl; }
    public void setRefReason(String reason) { this.refReason = reason; }
    public void setClinSum(String sum) { this.clinSum = sum; }
    public void setReqTests(String tests) { this.reqTests = tests; }
    public void setRefStatus(String status) { this.refStatus = status; }
    public void setApptId(String id) { this.apptId = id; }
    public void setRefNotes(String notes) { this.refNotes = notes; }
    public void setCreateDate(String date) { this.createDate = date; }
    public void setUpdateDate(String date) { this.updateDate = date; }
    
    public void dispatchRef() {
        this.refStatus = "Sent";
        this.updateDate = java.time.LocalDate.now().toString();
    }
    
    @Override
    public String toString() {
        return "Ref ID: " + refId + ", Patient: " + ptId +
               ", From: " + fromClinId + ", To: " + toClinId +
               ", Urgency: " + urgLvl + ", Status: " + refStatus;
    }
    
    public String formatAsCSV() {
        return refId + "," + ptId + "," + fromClinId + "," +
               toClinId + "," + fromFacId + "," +
               toFacId + "," + refDate + "," + urgLvl + "," +
               refReason + ",\"" + clinSum + "\"," +
               reqTests + "," + refStatus + "," + apptId + "," +
               "\"" + refNotes + "\"," + createDate + "," + updateDate;
    }
}
