package domain;

/**
 * MedicalRecordEntity for clinical records.
 * @author Vishal Hirani
 */
public class MedicalRecordEntity {
    private String recId;
    private String ptId;
    private String clinId;
    private String diag;
    private String symp;
    private String treat;
    private String recDate;
    private String recNotes;
    
    public MedicalRecordEntity() {
        this.recId = "";
        this.ptId = "";
        this.clinId = "";
        this.diag = "";
        this.symp = "";
        this.treat = "";
        this.recDate = "";
        this.recNotes = "";
    }
    
    public String getRecId() { return recId; }
    public void setRecId(String id) { this.recId = id; }
    public String getPtId() { return ptId; }
    public void setPtId(String id) { this.ptId = id; }
    public String getClinId() { return clinId; }
    public void setClinId(String id) { this.clinId = id; }
    public String getDiag() { return diag; }
    public void setDiag(String diag) { this.diag = diag; }
    public String getSymp() { return symp; }
    public void setSymp(String symp) { this.symp = symp; }
    public String getTreat() { return treat; }
    public void setTreat(String treat) { this.treat = treat; }
    public String getRecDate() { return recDate; }
    public void setRecDate(String date) { this.recDate = date; }
    public String getRecNotes() { return recNotes; }
    public void setRecNotes(String notes) { this.recNotes = notes; }
    
    public void appendNote(String note) {
        this.recNotes += "\n" + note;
    }
    
    @Override
    public String toString() {
        return "Rec ID: " + recId + ", Patient: " + ptId + 
               ", Diagnosis: " + diag + ", Date: " + recDate;
    }
}
