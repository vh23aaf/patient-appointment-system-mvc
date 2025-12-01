package ui;
import service.DataService;
import domain.PatientEntity;
import javax.swing.*;
import java.awt.*;

public class PtDialog extends JDialog {
    private DataService dSvc;
    private PatientEntity pt;
    private boolean isSaved = false;
    private JTextField[] flds = new JTextField[12];
    
    public PtDialog(Frame p, PatientEntity pt, DataService dSvc) {
        super(p, pt == null ? "Add Patient" : "Edit Patient", true);
        this.dSvc = dSvc;
        this.pt = pt;
        setSize(450, 550);
        setLocationRelativeTo(p);
        
        JPanel fp = new JPanel(new GridLayout(12, 2, 5, 5));
        String[] lbls = {"ID:", "First Name:", "Last Name:", "DOB:", "NHS:", "Sex:",
            "Phone:", "Email:", "Address:", "Postcode:", "Emerg Name:", "Emerg Phone:"};
        
        for (int i = 0; i < 12; i++) {
            flds[i] = new JTextField();
            fp.add(new JLabel(lbls[i]));
            fp.add(flds[i]);
        }
        
        if (pt != null) {
            flds[0].setText(pt.getId());
            flds[0].setEditable(false);
            flds[1].setText(pt.getFName());
            flds[2].setText(pt.getLName());
            flds[3].setText(pt.getDob());
            flds[4].setText(pt.getNhsNum());
            flds[5].setText(pt.getSex());
            flds[6].setText(pt.getContact());
            flds[7].setText(pt.getMail());
            flds[8].setText(pt.getAddr());
            flds[9].setText(pt.getPcode());
            flds[10].setText(pt.getEmergName());
            flds[11].setText(pt.getEmergPhone());
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
        if (id.isEmpty()) id = dSvc.genNewPtId();
        
        if (pt == null) {
            pt = new PatientEntity(id, flds[1].getText(), flds[2].getText(),
                flds[3].getText(), flds[4].getText(), flds[5].getText(),
                flds[6].getText(), flds[7].getText(), flds[8].getText(),
                flds[9].getText(), flds[10].getText(), flds[11].getText(),
                java.time.LocalDate.now().toString(), "S001");
            dSvc.addPt(pt);
        } else {
            pt.setFName(flds[1].getText());
            pt.setLName(flds[2].getText());
            pt.setDob(flds[3].getText());
            pt.setNhsNum(flds[4].getText());
            pt.setSex(flds[5].getText());
            pt.setContact(flds[6].getText());
            pt.setMail(flds[7].getText());
            pt.setAddr(flds[8].getText());
            pt.setPcode(flds[9].getText());
            pt.setEmergName(flds[10].getText());
            pt.setEmergPhone(flds[11].getText());
            dSvc.updatePt(pt.getId(), pt);
        }
        isSaved = true;
        dispose();
    }
    
    public boolean saved() { return isSaved; }
}
