package utility;
import domain.*;
import java.io.*;
import java.util.*;

/**
 * FileHandler - Scanner/PrintWriter approach.
 * @author Vishal Hirani
 */
public class FileHandler {
    private String dataDir;
    
    public FileHandler(String dir) {
        this.dataDir = dir;
    }
    
    public List<PatientEntity> loadPts() {
        List<PatientEntity> pts = new LinkedList<>();
        String fpath = dataDir + "/patients.csv";
        try {
            Scanner sc = new Scanner(new File(fpath));
            if (sc.hasNextLine()) sc.nextLine(); // skip header
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = parseCsvLine(line);
                if (data.length >= 14) {
                    PatientEntity p = new PatientEntity(data[0], data[1], data[2], data[3],
                        data[4], data[5], data[6], data[7], data[8], data[9],
                        data[10], data[11], data[12], data[13]);
                    pts.add(p);
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error loading patients: " + ex.getMessage());
        }
        return pts;
    }
    
    public List<AppointmentEntity> loadAppts() {
        List<AppointmentEntity> appts = new LinkedList<>();
        String fpath = dataDir + "/appointments.csv";
        try {
            Scanner sc = new Scanner(new File(fpath));
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = parseCsvLine(line);
                if (data.length >= 13) {
                    int dur = 0;
                    try { dur = Integer.parseInt(data[6]); } catch (Exception e) {}
                    AppointmentEntity a = new AppointmentEntity(data[0], data[1], data[2],
                        data[3], data[4], data[5], dur, data[7], data[8],
                        data[9], data[10], data[11], data[12]);
                    appts.add(a);
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error loading appointments: " + ex.getMessage());
        }
        return appts;
    }
    
    public List<PrescriptionEntity> loadRxs() {
        List<PrescriptionEntity> rxs = new LinkedList<>();
        String fpath = dataDir + "/prescriptions.csv";
        try {
            Scanner sc = new Scanner(new File(fpath));
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = parseCsvLine(line);
                if (data.length >= 15) {
                    int dur = 0, qty = 0;
                    try {
                        dur = Integer.parseInt(data[8]);
                        qty = Integer.parseInt(data[9]);
                    } catch (Exception e) {}
                    PrescriptionEntity rx = new PrescriptionEntity(data[0], data[1], data[2],
                        data[3], data[4], data[5], data[6], data[7], dur,
                        qty, data[10], data[11], data[12], data[13], data[14]);
                    rxs.add(rx);
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error loading prescriptions: " + ex.getMessage());
        }
        return rxs;
    }
    
    public List<ReferralEntity> loadRefs() {
        List<ReferralEntity> refs = new LinkedList<>();
        String fpath = dataDir + "/referrals.csv";
        try {
            Scanner sc = new Scanner(new File(fpath));
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = parseCsvLine(line);
                if (data.length >= 16) {
                    ReferralEntity r = new ReferralEntity();
                    r.setRefId(data[0]);
                    r.setPtId(data[1]);
                    r.setFromClinId(data[2]);
                    r.setToClinId(data[3]);
                    r.setFromFacId(data[4]);
                    r.setToFacId(data[5]);
                    r.setRefDate(data[6]);
                    r.setUrgLvl(data[7]);
                    r.setRefReason(data[8]);
                    r.setClinSum(data[9]);
                    r.setReqTests(data[10]);
                    r.setRefStatus(data[11]);
                    r.setApptId(data[12]);
                    r.setRefNotes(data[13]);
                    r.setCreateDate(data[14]);
                    r.setUpdateDate(data[15]);
                    refs.add(r);
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error loading referrals: " + ex.getMessage());
        }
        return refs;
    }
    
    public boolean savePts(List<PatientEntity> pts) {
        String fpath = dataDir + "/patients.csv";
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fpath));
            pw.println("patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address,postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id");
            for (PatientEntity p : pts) {
                pw.println(p.formatAsCSV());
            }
            pw.close();
            return true;
        } catch (IOException ex) {
            System.err.println("Error saving patients: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean saveAppts(List<AppointmentEntity> appts) {
        String fpath = dataDir + "/appointments.csv";
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fpath));
            pw.println("appointment_id,patient_id,clinician_id,facility_id,appointment_date,appointment_time,duration_minutes,appointment_type,status,reason_for_visit,notes,created_date,last_modified");
            for (AppointmentEntity a : appts) {
                pw.println(a.formatAsCSV());
            }
            pw.close();
            return true;
        } catch (IOException ex) {
            System.err.println("Error saving appointments: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean saveRxs(List<PrescriptionEntity> rxs) {
        String fpath = dataDir + "/prescriptions.csv";
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fpath));
            pw.println("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date");
            for (PrescriptionEntity rx : rxs) {
                pw.println(rx.formatAsCSV());
            }
            pw.close();
            return true;
        } catch (IOException ex) {
            System.err.println("Error saving prescriptions: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean saveRefs(List<ReferralEntity> refs) {
        String fpath = dataDir + "/referrals.csv";
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fpath));
            pw.println("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated");
            for (ReferralEntity r : refs) {
                pw.println(r.formatAsCSV());
            }
            pw.close();
            return true;
        } catch (IOException ex) {
            System.err.println("Error saving referrals: " + ex.getMessage());
            return false;
        }
    }
    
    private String[] parseCsvLine(String line) {
        List<String> res = new ArrayList<>();
        boolean inQuote = false;
        StringBuilder cur = new StringBuilder();
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuote = !inQuote;
            } else if (c == ',' && !inQuote) {
                res.add(cur.toString());
                cur = new StringBuilder();
            } else {
                cur.append(c);
            }
        }
        res.add(cur.toString());
        return res.toArray(new String[0]);
    }
}
