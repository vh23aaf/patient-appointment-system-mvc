package ui;
import service.DataService;
import domain.PrescriptionEntity;
import javax.swing.*;
import java.awt.*;

/**
 * RxDialog for prescription add/edit operations.
 * @author Vishal Hirani
 */
public class RxDialog extends JDialog {
    private DataService dSvc;
    private PrescriptionEntity rx;
    private boolean isSaved = false;

    private JTextField idFld;
    private JTextField ptIdFld;
    private JTextField clinIdFld;
    private JTextField medFld;
    private JTextField doseFld;
    private JTextField freqFld;
    private JTextField durFld;
    private JTextField qtyFld;
    private JTextField pharmFld;

    public RxDialog(Frame p, PrescriptionEntity rx, DataService dSvc) {
        super(p, rx == null ? "Add Prescription" : "Edit Prescription", true);
        this.dSvc = dSvc;
        this.rx = rx;
        setSize(450, 450);
        setLocationRelativeTo(p);

        JPanel fp = new JPanel(new GridLayout(9, 2, 5, 5));
        fp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize fields
        idFld = new JTextField();
        ptIdFld = new JTextField();
        clinIdFld = new JTextField();
        medFld = new JTextField();
        doseFld = new JTextField();
        freqFld = new JTextField();
        durFld = new JTextField();
        qtyFld = new JTextField();
        pharmFld = new JTextField();

        // Add labels and fields
        fp.add(new JLabel("Prescription ID:"));
        fp.add(idFld);
        fp.add(new JLabel("Patient ID:"));
        fp.add(ptIdFld);
        fp.add(new JLabel("Clinician ID:"));
        fp.add(clinIdFld);
        fp.add(new JLabel("Medication:"));
        fp.add(medFld);
        fp.add(new JLabel("Dosage:"));
        fp.add(doseFld);
        fp.add(new JLabel("Frequency:"));
        fp.add(freqFld);
        fp.add(new JLabel("Duration (days):"));
        fp.add(durFld);
        fp.add(new JLabel("Quantity:"));
        fp.add(qtyFld);
        fp.add(new JLabel("Pharmacy:"));
        fp.add(pharmFld);

        // If editing, populate fields
        if (rx != null) {
            idFld.setText(rx.getRxId());
            idFld.setEditable(false);
            ptIdFld.setText(rx.getPtId());
            clinIdFld.setText(rx.getClinId());
            medFld.setText(rx.getMedName());
            doseFld.setText(rx.getDose());
            freqFld.setText(rx.getFreq());
            durFld.setText(String.valueOf(rx.getDurDays()));
            qtyFld.setText(String.valueOf(rx.getQty()));
            pharmFld.setText(rx.getPharmName());
        }

        JPanel bp = new JPanel();
        JButton sb = new JButton("Save");
        sb.addActionListener(e -> save());
        JButton cb = new JButton("Cancel");
        cb.addActionListener(e -> dispose());
        bp.add(sb);
        bp.add(cb);

        add(fp, BorderLayout.CENTER);
        add(bp, BorderLayout.SOUTH);
    }

    private void save() {
        try {
            String id = idFld.getText().trim();
            if (id.isEmpty()) {
                id = dSvc.genNewRxId();
            }

            int dur = 7;
            int qty = 10;
            try {
                dur = Integer.parseInt(durFld.getText().trim());
                qty = Integer.parseInt(qtyFld.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Duration and Quantity must be numbers",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String today = java.time.LocalDate.now().toString();

            if (rx == null) {
                // Creating NEW prescription
                rx = new PrescriptionEntity(
                        id,
                        ptIdFld.getText().trim(),
                        clinIdFld.getText().trim(),
                        "", // appointmentId
                        today, // rxDate
                        medFld.getText().trim(),
                        doseFld.getText().trim(),
                        freqFld.getText().trim(),
                        dur,
                        qty,
                        "", // instructions
                        pharmFld.getText().trim(),
                        "Issued",
                        today, // issueDate
                        "" // collectionDate
                );
                dSvc.addRx(rx);
                JOptionPane.showMessageDialog(this,
                        "Prescription created successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // EDITING existing prescription
                rx.setPtId(ptIdFld.getText().trim());
                rx.setClinId(clinIdFld.getText().trim());
                rx.setMedName(medFld.getText().trim());
                rx.setDose(doseFld.getText().trim());
                rx.setFreq(freqFld.getText().trim());
                rx.setDurDays(dur);
                rx.setQty(qty);
                rx.setPharmName(pharmFld.getText().trim());

                dSvc.updateRx(rx.getRxId(), rx);
                JOptionPane.showMessageDialog(this,
                        "Prescription updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            isSaved = true;
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving prescription: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean saved() {
        return isSaved;
    }
}