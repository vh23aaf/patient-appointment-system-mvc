package service;

import domain.*;
import utility.FileHandler;
import java.util.*;

/**
 * DataService - Central business logic layer.
 * @author Vishal Hirani
 */
public class DataService {
    private List<PatientEntity> ptList;
    private List<AppointmentEntity> apptList;
    private List<PrescriptionEntity> rxList;
    private FileHandler fileHdlr;
    private ReferralService refSvc;
    
    public DataService(String dataDir) {
        this.fileHdlr = new FileHandler(dataDir);
        this.ptList = new LinkedList<>();
        this.apptList = new LinkedList<>();
        this.rxList = new LinkedList<>();
        this.refSvc = ReferralService.getInst();
        initData();
    }
    
    public void initData() {
        System.out.println("Initializing data...");
        ptList = fileHdlr.loadPts();
        apptList = fileHdlr.loadAppts();
        rxList = fileHdlr.loadRxs();
        
        List<ReferralEntity> loadedRefs = fileHdlr.loadRefs();
        for (ReferralEntity r : loadedRefs) {
            refSvc.addRef(r);
        }
        
        System.out.println("Data loaded: " + ptList.size() + " patients, " +
                         apptList.size() + " appointments, " +
                         rxList.size() + " prescriptions, " +
                         refSvc.getRefCnt() + " referrals");
    }
    
    public boolean persistData() {
        boolean ok = true;
        ok &= fileHdlr.savePts(ptList);
        ok &= fileHdlr.saveAppts(apptList);
        ok &= fileHdlr.saveRxs(rxList);
        ok &= fileHdlr.saveRefs(refSvc.fetchAllRefs());
        return ok;
    }
    
    // Patient ops
    public List<PatientEntity> fetchAllPts() { return new LinkedList<>(ptList); }
    
    public PatientEntity findPtById(String id) {
        for (PatientEntity p : ptList) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
    
    public boolean addPt(PatientEntity pt) {
        if (pt != null) {
            ptList.add(pt);
            return true;
        }
        return false;
    }
    
    public boolean updatePt(String id, PatientEntity upd) {
        for (int i = 0; i < ptList.size(); i++) {
            if (ptList.get(i).getId().equals(id)) {
                ptList.set(i, upd);
                return true;
            }
        }
        return false;
    }
    
    public boolean removePt(String id) {
        return ptList.removeIf(p -> p.getId().equals(id));
    }
    
    // Appointment ops
    public List<AppointmentEntity> fetchAllAppts() { return new LinkedList<>(apptList); }
    
    public AppointmentEntity findApptById(String id) {
        for (AppointmentEntity a : apptList) {
            if (a.getApptId().equals(id)) return a;
        }
        return null;
    }
    
    public boolean addAppt(AppointmentEntity appt) {
        if (appt != null) {
            apptList.add(appt);
            return true;
        }
        return false;
    }
    
    public boolean updateAppt(String id, AppointmentEntity upd) {
        for (int i = 0; i < apptList.size(); i++) {
            if (apptList.get(i).getApptId().equals(id)) {
                apptList.set(i, upd);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeAppt(String id) {
        return apptList.removeIf(a -> a.getApptId().equals(id));
    }
    
    // Prescription ops
    public List<PrescriptionEntity> fetchAllRxs() { return new LinkedList<>(rxList); }
    
    public PrescriptionEntity findRxById(String id) {
        for (PrescriptionEntity rx : rxList) {
            if (rx.getRxId().equals(id)) return rx;
        }
        return null;
    }
    
    public boolean addRx(PrescriptionEntity rx) {
        if (rx != null) {
            rxList.add(rx);
            writeRxToFile(rx);
            return true;
        }
        return false;
    }
    
    public boolean updateRx(String id, PrescriptionEntity upd) {
        for (int i = 0; i < rxList.size(); i++) {
            if (rxList.get(i).getRxId().equals(id)) {
                rxList.set(i, upd);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeRx(String id) {
        return rxList.removeIf(rx -> rx.getRxId().equals(id));
    }
    
    private void writeRxToFile(PrescriptionEntity rx) {
        try {
            java.io.File outDir = new java.io.File("output");
            if (!outDir.exists()) outDir.mkdirs();
            
            String fname = "output/prescription_" + rx.getRxId() + ".txt";
            java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(fname));
            
            pw.println("=".repeat(70));
            pw.println("PRESCRIPTION");
            pw.println("=".repeat(70));
            pw.println("Prescription ID: " + rx.getRxId());
            pw.println("Patient ID: " + rx.getPtId());
            pw.println("Clinician ID: " + rx.getClinId());
            pw.println("Date: " + rx.getRxDate());
            pw.println("Medication: " + rx.getMedName());
            pw.println("Dosage: " + rx.getDose());
            pw.println("Frequency: " + rx.getFreq());
            pw.println("Duration: " + rx.getDurDays() + " days");
            pw.println("Quantity: " + rx.getQty());
            pw.println("Instructions: " + rx.getInstruct());
            pw.println("Pharmacy: " + rx.getPharmName());
            pw.println("Status: " + rx.getRxStatus());
            pw.println("=".repeat(70));
            
            pw.close();
            System.out.println("Prescription written to: " + fname);
        } catch (java.io.IOException ex) {
            System.err.println("Error writing prescription: " + ex.getMessage());
        }
    }
    
    // Referral ops
    public List<ReferralEntity> fetchAllRefs() {
        return refSvc.fetchAllRefs();
    }
    
    public boolean addRef(ReferralEntity ref) {
        return refSvc.addRef(ref);
    }
    
    public boolean transmitRef(ReferralEntity ref) {
        return refSvc.transmitRef(ref);
    }
    
    public String genNewPtId() {
        int max = 0;
        for (PatientEntity p : ptList) {
            String id = p.getId();
            if (id.startsWith("P")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {}
            }
        }
        return String.format("P%03d", max + 1);
    }
    
    public String genNewApptId() {
        int max = 0;
        for (AppointmentEntity a : apptList) {
            String id = a.getApptId();
            if (id.startsWith("A")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {}
            }
        }
        return String.format("A%03d", max + 1);
    }
    
    public String genNewRxId() {
        int max = 0;
        for (PrescriptionEntity rx : rxList) {
            String id = rx.getRxId();
            if (id.startsWith("RX")) {
                try {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {}
            }
        }
        return String.format("RX%03d", max + 1);
    }
}
