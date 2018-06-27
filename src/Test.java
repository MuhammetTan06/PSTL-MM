import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Wed Jun 20 10:41:56 CEST 2018
 */



/**
 * @author Muhammet Tan
 */
public class Test extends JFrame {
	public Test() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Muhammet Tan
		label1 = new JLabel();
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		button4 = new JButton();

		//======== this ========
		setTitle("La fenetre de muha");
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Le label de muha");
		contentPane.add(label1);
		label1.setBounds(new Rectangle(new Point(230, 75), label1.getPreferredSize()));

		//---- button1 ----
		button1.setText("dezfz");
		contentPane.add(button1);
		button1.setBounds(new Rectangle(new Point(215, 155), button1.getPreferredSize()));

		//---- button2 ----
		button2.setText("cebre");
		contentPane.add(button2);
		button2.setBounds(new Rectangle(new Point(300, 155), button2.getPreferredSize()));

		//---- button3 ----
		button3.setText("dzada");
		contentPane.add(button3);
		button3.setBounds(new Rectangle(new Point(95, 130), button3.getPreferredSize()));

		//---- button4 ----
		button4.setText("dedio");
		contentPane.add(button4);
		button4.setBounds(new Rectangle(new Point(140, 240), button4.getPreferredSize()));

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Muhammet Tan
	private JLabel label1;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
