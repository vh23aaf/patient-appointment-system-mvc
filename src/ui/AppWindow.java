package ui;
import service.DataService;
import domain.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

/**
 * AppWindow - Main GUI.
 * @author Vishal Hirani
 */
public class AppWindow extends JFrame {
    private DataService dSvc;
    private JTabbedPane tabs;
    private DefaultTableModel ptModel, apptModel, rxModel, refModel;
    private JTable ptTbl, apptTbl, rxTbl, refTbl;
    
    public AppWindow(DataService dSvc) {
        this.dSvc = dSvc;
        setTitle("Healthcare System - Vishal Hirani");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setup();
        loadAll();
    }
    
    private void setup() {
        tabs = new JTabbedPane();
        tabs.addTab("Patients", mkPtPanel());
        tabs.addTab("Appointments", mkApptPanel());
        tabs.addTab("Prescriptions", mkRxPanel());
        tabs.addTab("Referrals", mkRefPanel());
        add(tabs);
        
        JMenuBar mb = new JMenuBar();
        JMenu fm = new JMenu("File");
        JMenuItem si = new JMenuItem("Save Data");
        si.addActionListener(e -> saveAll());
        fm.add(si);
        setJMenuBar(mb);
    }
    
    private JPanel mkPtPanel() {
        JPanel p = new JPanel(new BorderLayout());
        String[] cols = {"ID", "First", "Last", "DOB", "NHS", "Sex", "Email"};
        ptModel = new DefaultTableModel(cols, 0);
        ptTbl = new JTable(ptModel);
        p.add(new JScrollPane(ptTbl), BorderLayout.CENTER);
        
        JPanel bp = new JPanel();
        JButton ab = new JButton("Add");
        JButton eb = new JButton("Edit");
        JButton db = new JButton("Delete");
        ab.addActionListener(e -> addPt());
        eb.addActionListener(e -> editPt());
        db.addActionListener(e -> delPt());
        bp.add(ab); bp.add(eb); bp.add(db);
        p.add(bp, BorderLayout.SOUTH);
        return p;
    }
    
    private JPanel mkApptPanel() {
        JPanel p = new JPanel(new BorderLayout());
        String[] cols = {"ID", "Patient", "Clinician", "Date", "Time", "Type", "Status"};
        apptModel = new DefaultTableModel(cols, 0);
        apptTbl = new JTable(apptModel);
        p.add(new JScrollPane(apptTbl), BorderLayout.CENTER);
        
        JPanel bp = new JPanel();
        JButton ab = new JButton("Add");
        JButton eb = new JButton("Edit");
        JButton db = new JButton("Delete");
        ab.addActionListener(e -> addAppt());
        eb.addActionListener(e -> editAppt());
        db.addActionListener(e -> delAppt());
        bp.add(ab); bp.add(eb); bp.add(db);
        p.add(bp, BorderLayout.SOUTH);
        return p;
    }
    
    private JPanel mkRxPanel() {
        JPanel p = new JPanel(new BorderLayout());
        String[] cols = {"ID", "Patient", "Clinician", "Medication", "Dose", "Status"};
        rxModel = new DefaultTableModel(cols, 0);
        rxTbl = new JTable(rxModel);
        p.add(new JScrollPane(rxTbl), BorderLayout.CENTER);
        
        JPanel bp = new JPanel();
        JButton ab = new JButton("Add");
        JButton eb = new JButton("Edit");
        JButton db = new JButton("Delete");
        ab.addActionListener(e -> addRx());
        eb.addActionListener(e -> editRx());
        db.addActionListener(e -> delRx());
        bp.add(ab); bp.add(eb); bp.add(db);
        p.add(bp, BorderLayout.SOUTH);
        return p;
    }
    
    private JPanel mkRefPanel() {
        JPanel p = new JPanel(new BorderLayout());
        String[] cols = {"ID", "Patient", "From", "To", "Urgency", "Status"};
        refModel = new DefaultTableModel(cols, 0);
        refTbl = new JTable(refModel);
        p.add(new JScrollPane(refTbl), BorderLayout.CENTER);
        
        JPanel bp = new JPanel();
        JButton ab = new JButton("Add");
        JButton sb = new JButton("Send");
        ab.addActionListener(e -> addRef());
        sb.addActionListener(e -> sendRef());
        bp.add(ab); bp.add(sb);
        p.add(bp, BorderLayout.SOUTH);
        return p;
    }
    
    private void loadAll() {
        loadPts(); loadAppts(); loadRxs(); loadRefs();
    }
    
    private void loadPts() {
        ptModel.setRowCount(0);
        for (PatientEntity p : dSvc.fetchAllPts()) {
            ptModel.addRow(new Object[]{p.getId(), p.getFName(), p.getLName(),
                p.getDob(), p.getNhsNum(), p.getSex(), p.getMail()});
        }
    }
    
    private void loadAppts() {
        apptModel.setRowCount(0);
        for (AppointmentEntity a : dSvc.fetchAllAppts()) {
            apptModel.addRow(new Object[]{a.getApptId(), a.getPtId(), a.getClinId(),
                a.getApptDate(), a.getApptTime(), a.getApptType(), a.getApptStatus()});
        }
    }
    
    private void loadRxs() {
        rxModel.setRowCount(0);
        for (PrescriptionEntity rx : dSvc.fetchAllRxs()) {
            rxModel.addRow(new Object[]{rx.getRxId(), rx.getPtId(), rx.getClinId(),
                rx.getMedName(), rx.getDose(), rx.getRxStatus()});
        }
    }
    
    private void loadRefs() {
        refModel.setRowCount(0);
        for (ReferralEntity r : dSvc.fetchAllRefs()) {
            refModel.addRow(new Object[]{r.getRefId(), r.getPtId(), r.getFromClinId(),
                r.getToClinId(), r.getUrgLvl(), r.getRefStatus()});
        }
    }
    
    private void addPt() {
        PtDialog d = new PtDialog(this, null, dSvc);
        d.setVisible(true);
        if (d.saved()) { loadPts(); saveAll(); }
    }
    
    private void editPt() {
        int r = ptTbl.getSelectedRow();
        if (r >= 0) {
            String id = (String) ptModel.getValueAt(r, 0);
            PtDialog d = new PtDialog(this, dSvc.findPtById(id), dSvc);
            d.setVisible(true);
            if (d.saved()) { loadPts(); saveAll(); }
        }
    }
    
    private void delPt() {
        int r = ptTbl.getSelectedRow();
        if (r >= 0) {
            dSvc.removePt((String) ptModel.getValueAt(r, 0));
            loadPts(); saveAll();
        }
    }
    
    private void addAppt() {
        ApptDialog d = new ApptDialog(this, null, dSvc);
        d.setVisible(true);
        if (d.saved()) { loadAppts(); saveAll(); }
    }
    
    private void editAppt() {
        int r = apptTbl.getSelectedRow();
        if (r >= 0) {
            String id = (String) apptModel.getValueAt(r, 0);
            ApptDialog d = new ApptDialog(this, dSvc.findApptById(id), dSvc);
            d.setVisible(true);
            if (d.saved()) { loadAppts(); saveAll(); }
        }
    }
    
    private void delAppt() {
        int r = apptTbl.getSelectedRow();
        if (r >= 0) {
            dSvc.removeAppt((String) apptModel.getValueAt(r, 0));
            loadAppts(); saveAll();
        }
    }
    
    private void addRx() {
        RxDialog d = new RxDialog(this, null, dSvc);
        d.setVisible(true);
        if (d.saved()) { loadRxs(); saveAll(); }
    }
    
    private void editRx() {
        int r = rxTbl.getSelectedRow();
        if (r >= 0) {
            String id = (String) rxModel.getValueAt(r, 0);
            RxDialog d = new RxDialog(this, dSvc.findRxById(id), dSvc);
            d.setVisible(true);
            if (d.saved()) { loadRxs(); saveAll(); }
        }
    }
    
    private void delRx() {
        int r = rxTbl.getSelectedRow();
        if (r >= 0) {
            dSvc.removeRx((String) rxModel.getValueAt(r, 0));
            loadRxs(); saveAll();
        }
    }
    
    private void addRef() {
        RefDialog d = new RefDialog(this, dSvc);
        d.setVisible(true);
        if (d.saved()) { loadRefs(); saveAll(); }
    }
    
    private void sendRef() {
        int r = refTbl.getSelectedRow();
        if (r >= 0) {
            String id = (String) refModel.getValueAt(r, 0);
            ReferralEntity ref = null;
            for (ReferralEntity re : dSvc.fetchAllRefs()) {
                if (re.getRefId().equals(id)) { ref = re; break; }
            }
            if (ref != null) {
                dSvc.transmitRef(ref);
                JOptionPane.showMessageDialog(this, "Referral sent!");
                loadRefs();
            }
        }
    }
    
    private void saveAll() {
        dSvc.persistData();
    }
}
