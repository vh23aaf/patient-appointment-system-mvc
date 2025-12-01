package service;

import domain.ReferralEntity;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

/**
 * ReferralService - Singleton with LAZY initialization.
 * @author Vishal Hirani
 */
public class ReferralService {
    private static ReferralService inst = null;
    private List<ReferralEntity> refList;
    private int refCtr;
    
    private ReferralService() {
        this.refList = new LinkedList<>();
        this.refCtr = 2000;
        System.out.println("[LAZY SINGLETON] ReferralService initialized");
    }
    
    public static synchronized ReferralService getInst() {
        if (inst == null) {
            inst = new ReferralService();
        }
        return inst;
    }
    
    public boolean addRef(ReferralEntity ref) {
        if (ref != null) {
            refList.add(ref);
            logOp("Added referral: " + ref.getRefId());
            return true;
        }
        return false;
    }
    
    public List<ReferralEntity> fetchAllRefs() {
        return new LinkedList<>(refList);
    }
    
    public ReferralEntity findRefById(String refId) {
        for (ReferralEntity ref : refList) {
            if (ref.getRefId().equals(refId)) {
                return ref;
            }
        }
        return null;
    }
    
    public boolean modifyRef(String refId, ReferralEntity updRef) {
        for (int i = 0; i < refList.size(); i++) {
            if (refList.get(i).getRefId().equals(refId)) {
                refList.set(i, updRef);
                logOp("Modified referral: " + refId);
                return true;
            }
        }
        return false;
    }
    
    public String genRefId() {
        return "R" + (++refCtr);
    }
    
    public boolean transmitRef(ReferralEntity ref) {
        if (ref != null) {
            ref.dispatchRef();
            writeRefToFile(ref);
            logOp("Transmitted referral: " + ref.getRefId());
            return true;
        }
        return false;
    }
    
    private void writeRefToFile(ReferralEntity ref) {
        try {
            File outDir = new File("output");
            if (!outDir.exists()) outDir.mkdirs();
            
            String fname = "output/referral_" + ref.getRefId() + ".txt";
            PrintWriter pw = new PrintWriter(new FileWriter(fname));
            
            pw.println("=".repeat(70));
            pw.println("REFERRAL NOTIFICATION");
            pw.println("=".repeat(70));
            pw.println("Referral ID: " + ref.getRefId());
            pw.println("Patient ID: " + ref.getPtId());
            pw.println("From Clinician: " + ref.getFromClinId());
            pw.println("To Clinician: " + ref.getToClinId());
            pw.println("Date: " + ref.getRefDate());
            pw.println("Urgency Level: " + ref.getUrgLvl());
            pw.println("Reason: " + ref.getRefReason());
            pw.println("Clinical Summary: " + ref.getClinSum());
            pw.println("Requested Tests: " + ref.getReqTests());
            pw.println("=".repeat(70));
            
            pw.close();
            System.out.println("Referral written to: " + fname);
        } catch (IOException ex) {
            System.err.println("Error writing referral: " + ex.getMessage());
        }
    }
    
    private void logOp(String op) {
        String ts = java.time.LocalDateTime.now().toString();
        System.out.println("[" + ts + "] " + op);
    }
    
    public int getRefCnt() {
        return refList.size();
    }
}
