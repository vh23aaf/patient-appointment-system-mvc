package domain;

/**
 * AppointmentEntity for scheduling.
 * @author Vishal Hirani
 */
public class AppointmentEntity {
    private String apptId;
    private String ptId;
    private String clinId;
    private String facId;
    private String apptDate;
    private String apptTime;
    private int durMins;
    private String apptType;
    private String apptStatus;
    private String visitReason;
    private String apptNotes;
    private String createdOn;
    private String modifiedOn;
    
    public AppointmentEntity() {
        this.apptId = "";
        this.ptId = "";
        this.clinId = "";
        this.facId = "";
        this.apptDate = "";
        this.apptTime = "";
        this.durMins = 0;
        this.apptType = "";
        this.apptStatus = "Scheduled";
        this.visitReason = "";
        this.apptNotes = "";
        this.createdOn = "";
        this.modifiedOn = "";
    }
    
    public AppointmentEntity(String apptId, String ptId, String clinId,
                            String facId, String apptDate, String apptTime,
                            int durMins, String apptType, String apptStatus,
                            String visitReason, String apptNotes, String createdOn,
                            String modifiedOn) {
        this.apptId = apptId;
        this.ptId = ptId;
        this.clinId = clinId;
        this.facId = facId;
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.durMins = durMins;
        this.apptType = apptType;
        this.apptStatus = apptStatus;
        this.visitReason = visitReason;
        this.apptNotes = apptNotes;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
    
    // Accessors
    public String getApptId() { return apptId; }
    public String getPtId() { return ptId; }
    public String getClinId() { return clinId; }
    public String getFacId() { return facId; }
    public String getApptDate() { return apptDate; }
    public String getApptTime() { return apptTime; }
    public int getDurMins() { return durMins; }
    public String getApptType() { return apptType; }
    public String getApptStatus() { return apptStatus; }
    public String getVisitReason() { return visitReason; }
    public String getApptNotes() { return apptNotes; }
    public String getCreatedOn() { return createdOn; }
    public String getModifiedOn() { return modifiedOn; }
    
    // Mutators
    public void setApptId(String id) { this.apptId = id; }
    public void setPtId(String id) { this.ptId = id; }
    public void setClinId(String id) { this.clinId = id; }
    public void setFacId(String id) { this.facId = id; }
    public void setApptDate(String date) { this.apptDate = date; }
    public void setApptTime(String time) { this.apptTime = time; }
    public void setDurMins(int mins) { this.durMins = mins; }
    public void setApptType(String type) { this.apptType = type; }
    public void setApptStatus(String status) { this.apptStatus = status; }
    public void setVisitReason(String reason) { this.visitReason = reason; }
    public void setApptNotes(String notes) { this.apptNotes = notes; }
    public void setCreatedOn(String date) { this.createdOn = date; }
    public void setModifiedOn(String date) { this.modifiedOn = date; }
    
    public boolean bookAppt() {
        if (!apptDate.isEmpty() && !apptTime.isEmpty()) {
            this.apptStatus = "Scheduled";
            this.modifiedOn = java.time.LocalDate.now().toString();
            return true;
        }
        return false;
    }
    
    public boolean changeAppt(String newDate, String newTime) {
        if (!newDate.isEmpty() && !newTime.isEmpty()) {
            this.apptDate = newDate;
            this.apptTime = newTime;
            this.modifiedOn = java.time.LocalDate.now().toString();
            return true;
        }
        return false;
    }
    
    public boolean cancelAppt() {
        this.apptStatus = "Cancelled";
        this.modifiedOn = java.time.LocalDate.now().toString();
        return true;
    }
    
    @Override
    public String toString() {
        return "Appt ID: " + apptId + ", Patient: " + ptId + 
               ", Clinician: " + clinId + ", Date: " + apptDate + 
               " " + apptTime + ", Status: " + apptStatus;
    }
    
    public String formatAsCSV() {
        return apptId + "," + ptId + "," + clinId + "," + 
               facId + "," + apptDate + "," + apptTime + "," + 
               durMins + "," + apptType + "," + apptStatus + "," + 
               visitReason + ",\"" + apptNotes + "\"," + createdOn + "," + modifiedOn;
    }
}
