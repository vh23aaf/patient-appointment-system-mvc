package ui;
import service.DataService;
import domain.AppointmentEntity;
import javax.swing.*;
import java.awt.*;

public class ApptDialog extends JDialog {
    private DataService dSvc;
    private AppointmentEntity appt;
    private boolean isSaved = false;
    private JTextField[] flds = new JTextField[9];
    
    public ApptDialog(Frame p, AppointmentEntity appt, DataService dSvc) {
        super(p, appt == null ? "Add Appointment" : "Edit Appointment", true);
        this.dSvc = dSvc;
        this.appt = appt;
        setSize(450, 450);
        setLocationRelativeTo(p);
        
        JPanel fp = new JPanel(new GridLayout(9, 2, 5, 5));
        String[] lbls = {"ID:", "Patient ID:", "Clinician ID:", "Facility ID:",
            "Date:", "Time:", "Duration:", "Type:", "Status:"};
        
        for (int i = 0; i < 9; i++) {
            flds[i] = new JTextField();
            fp.add(new JLabel(lbls[i]));
            fp.add(flds[i]);
        }
        
        if (appt != null) {
            flds[0].setText(appt.getApptId());
            flds[0].setEditable(false);
            flds[1].setText(appt.getPtId());
            flds[2].setText(appt.getClinId());
            flds[3].setText(appt.getFacId());
            flds[4].setText(appt.getApptDate());
            flds[5].setText(appt.getApptTime());
            flds[6].setText(String.valueOf(appt.getDurMins()));
            flds[7].setText(appt.getApptType());
            flds[8].setText(appt.getApptStatus());
        }
        
        JPanel bp = new JPanel();
        JButton sb = new JButton("Save");
        sb.addActionListener(e -> save());
        JButton cb = new JButton("Cancel");
        cb.addActionListener(e -> dispose());
        bp.add(sb); bp.add(cb);
        
        add(fp, BorderLayout.CENTER);
        add(bp, BorderLayout.SOUTH);
    }
    
    private void save() {
        String id = flds[0].getText().trim();
        if (id.isEmpty()) id = dSvc.genNewApptId();
        
        int dur = 15;
        try { dur = Integer.parseInt(flds[6].getText()); } catch (Exception e) {}
        
        String today = java.time.LocalDate.now().toString();
        
        if (appt == null) {
            appt = new AppointmentEntity(id, flds[1].getText(), flds[2].getText(),
                flds[3].getText(), flds[4].getText(), flds[5].getText(),
                dur, flds[7].getText(), flds[8].getText(), "", "", today, today);
            dSvc.addAppt(appt);
        } else {
            appt.setPtId(flds[1].getText());
            appt.setClinId(flds[2].getText());
            appt.setFacId(flds[3].getText());
            appt.setApptDate(flds[4].getText());
            appt.setApptTime(flds[5].getText());
            appt.setDurMins(dur);
            appt.setApptType(flds[7].getText());
            appt.setApptStatus(flds[8].getText());
            dSvc.updateAppt(appt.getApptId(), appt);
        }
        isSaved = true;
        dispose();
    }
    
    public boolean saved() { return isSaved; }
}
