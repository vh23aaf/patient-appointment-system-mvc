package domain;

/**
 * PrescriptionEntity - medicine prescriptions.
 * @author Vishal Hirani
 */
public class PrescriptionEntity {
    private String rxId;
    private String ptId;
    private String clinId;
    private String apptId;
    private String rxDate;
    private String medName;
    private String dose;
    private String freq;
    private int durDays;
    private int qty;
    private String instruct;
    private String pharmName;
    private String rxStatus;
    private String issDate;
    private String collDate;
    
    public PrescriptionEntity() {
        this.rxId = "";
        this.ptId = "";
        this.clinId = "";
        this.apptId = "";
        this.rxDate = "";
        this.medName = "";
        this.dose = "";
        this.freq = "";
        this.durDays = 0;
        this.qty = 0;
        this.instruct = "";
        this.pharmName = "";
        this.rxStatus = "Issued";
        this.issDate = "";
        this.collDate = "";
    }
    
    public PrescriptionEntity(String rxId, String ptId, String clinId,
                             String apptId, String rxDate, String medName,
                             String dose, String freq, int durDays, int qty,
                             String instruct, String pharmName, String rxStatus,
                             String issDate, String collDate) {
        this.rxId = rxId;
        this.ptId = ptId;
        this.clinId = clinId;
        this.apptId = apptId;
        this.rxDate = rxDate;
        this.medName = medName;
        this.dose = dose;
        this.freq = freq;
        this.durDays = durDays;
        this.qty = qty;
        this.instruct = instruct;
        this.pharmName = pharmName;
        this.rxStatus = rxStatus;
        this.issDate = issDate;
        this.collDate = collDate;
    }
    
    // Getters
    public String getRxId() { return rxId; }
    public String getPtId() { return ptId; }
    public String getClinId() { return clinId; }
    public String getApptId() { return apptId; }
    public String getRxDate() { return rxDate; }
    public String getMedName() { return medName; }
    public String getDose() { return dose; }
    public String getFreq() { return freq; }
    public int getDurDays() { return durDays; }
    public int getQty() { return qty; }
    public String getInstruct() { return instruct; }
    public String getPharmName() { return pharmName; }
    public String getRxStatus() { return rxStatus; }
    public String getIssDate() { return issDate; }
    public String getCollDate() { return collDate; }
    
    // Setters
    public void setRxId(String id) { this.rxId = id; }
    public void setPtId(String id) { this.ptId = id; }
    public void setClinId(String id) { this.clinId = id; }
    public void setApptId(String id) { this.apptId = id; }
    public void setRxDate(String date) { this.rxDate = date; }
    public void setMedName(String name) { this.medName = name; }
    public void setDose(String dose) { this.dose = dose; }
    public void setFreq(String freq) { this.freq = freq; }
    public void setDurDays(int days) { this.durDays = days; }
    public void setQty(int qty) { this.qty = qty; }
    public void setInstruct(String instruct) { this.instruct = instruct; }
    public void setPharmName(String name) { this.pharmName = name; }
    public void setRxStatus(String status) { this.rxStatus = status; }
    public void setIssDate(String date) { this.issDate = date; }
    public void setCollDate(String date) { this.collDate = date; }
    
    public boolean checkValid() {
        return !medName.isEmpty() && !dose.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Rx ID: " + rxId + ", Patient: " + ptId +
               ", Med: " + medName + ", Dose: " + dose +
               ", Status: " + rxStatus;
    }
    
    public String formatAsCSV() {
        return rxId + "," + ptId + "," + clinId + "," +
               apptId + "," + rxDate + "," + medName + "," +
               dose + "," + freq + "," + durDays + "," + qty + "," +
               instruct + "," + pharmName + "," + rxStatus + "," +
               issDate + "," + collDate;
    }
}
