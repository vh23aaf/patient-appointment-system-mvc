package ui;
import service.DataService;
import service.ReferralService;
import domain.ReferralEntity;
import javax.swing.*;
import java.awt.*;

public class RefDialog extends JDialog {
    private DataService dSvc;
    private boolean isSaved = false;
    private JTextField[] flds = new JTextField[7];
    
    public RefDialog(Frame p, DataService dSvc) {
        super(p, "Add Referral", true);
        this.dSvc = dSvc;
        setSize(450, 400);
        setLocationRelativeTo(p);
        
        JPanel fp = new JPanel(new GridLayout(7, 2, 5, 5));
        String[] lbls = {"Patient ID:", "From Clinician:", "To Clinician:",
            "From Facility:", "To Facility:", "Urgency:", "Reason:"};
        
        for (int i = 0; i < 7; i++) {
            flds[i] = new JTextField();
            fp.add(new JLabel(lbls[i]));
            fp.add(flds[i]);
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
        ReferralService rs = ReferralService.getInst();
        String id = rs.genRefId();
        String today = java.time.LocalDate.now().toString();
        
        ReferralEntity ref = new ReferralEntity();
        ref.setRefId(id);
        ref.setPtId(flds[0].getText());
        ref.setFromClinId(flds[1].getText());
        ref.setToClinId(flds[2].getText());
        ref.setFromFacId(flds[3].getText());
        ref.setToFacId(flds[4].getText());
        ref.setRefDate(today);
        ref.setUrgLvl(flds[5].getText());
        ref.setRefReason(flds[6].getText());
        ref.setClinSum("");
        ref.setReqTests("");
        ref.setRefStatus("New");
        ref.setCreateDate(today);
        ref.setUpdateDate(today);
        
        dSvc.addRef(ref);
        isSaved = true;
        dispose();
    }
    
    public boolean saved() { return isSaved; }
}
