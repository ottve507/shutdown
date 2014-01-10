import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Window extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	String m;
	Readwriter message = new Readwriter();
	JTextField tf;

	public Window() {
		// Fixar JFramen
		super("Ottos avstängningsprogram");
		setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Lägger till variabler (label och knappar)
		JLabel label = new JLabel(
				"Select the number of minutes for your shutdown timer");
		ButtonGroup bgroup = new ButtonGroup();
		JRadioButton b1 = new JRadioButton("10 min");
		JRadioButton b2 = new JRadioButton("15 min");
		JRadioButton b3 = new JRadioButton("20 min");
		JRadioButton b4 = new JRadioButton("Enter your own preference: ");
		tf = new JTextField("30");
		JButton submit = new JButton("Shutdown");
		JButton abort = new JButton("Abort");

		// Add power (Action), this hänvisar till actionperformmetoden nedan
		b1.addActionListener(this);
		b1.setActionCommand("10");
		b2.addActionListener(this);
		b2.setActionCommand("15");
		b3.addActionListener(this);
		b3.setActionCommand("20");
		b4.addActionListener(this);
		b4.setActionCommand("trollUnge");
		submit.addActionListener(new ActionListener() { // Fixar ett event vid
														// submit

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean t = true;
				if (m.equals("trollUnge")){
					t=false;
					try{
						Integer.parseInt(tf.getText());
						t=true;
					}
					catch(Exception e1){
						t=false;
					}
					try {
						message.write(tf.getText(), 1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (t){
				Shutoff();
				}
			}

		});
		abort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				m = "do shell script \"sudo killall shutdown\" with administrator privileges";
				try {
					message.write(m, 0);
					Shutoff();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// Grupperar knapparna (så man bara kan välja ett alternativ)
		bgroup.add(b1);
		bgroup.add(b2);
		bgroup.add(b3);
		bgroup.add(b4);

		// Lägger till paneler för knapparna
		JPanel p = new JPanel(); // För label
		JPanel b = new JPanel(new GridBagLayout()); // för radiobuttons
		JPanel s = new JPanel(); // för submit och abort

		// Layout för radioknappar
		GridBagConstraints radio = new GridBagConstraints();
		radio.insets = new Insets(10, 10, 10, 10);
		radio.gridy = 1;
		b.add(b1, radio);
		radio.gridy = 2;
		b.add(b2, radio);
		radio.gridy = 3;
		b.add(b3, radio);
		radio.gridy = 4;
		b.add(b4, radio);
		b.add(tf, radio);

		// Lägger till objekt till paneler (radio gjorda ovan)
		p.add(label);
		s.add(submit);
		s.add(abort);

		// Lägger till panelen till JFrame
		add(p, BorderLayout.NORTH);
		add(b, BorderLayout.WEST);
		add(s, BorderLayout.SOUTH);

	}

	public void Shutoff() {
		Process ls = null;
		BufferedReader input;

		String[] cmd = { "osascript", "shutdown.scpt" };

		try {
			ls = Runtime.getRuntime().exec(cmd);
			input = new BufferedReader(new InputStreamReader(
					ls.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		m = arg0.getActionCommand();
		
			try {
				message.write(m, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		}
	}
}
