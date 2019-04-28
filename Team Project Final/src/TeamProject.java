import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JSeparator;

// Java Team Project POS Java Lafitte Team 1.4
// Chase Hoover, Brad Sutton, Madison Cook, Karo Anthony, Dalton Wright

@SuppressWarnings("serial")
public class TeamProject extends JFrame implements ActionListener {

	final JFrame frame = new JFrame("Display Mode");

	// create list
	DefaultListModel<Order> data = new DefaultListModel<Order>();
	JList<Order> orderItemList = new JList<Order>(data);
	JScrollPane jsp = new JScrollPane(orderItemList);

	//create buttons
	JButton btnAddItem, btnRemoveItem, btnEditItem, btnPay, btnPreview;
	JButton btnPrint = new JButton("Print");
	JButton btnCoupon, btnOverride;

	//create combo boxes
	JComboBox chooseBrand, chooseColor, chooseSize, chooseQty;
	JComboBox<String> chooseType = new JComboBox<String>();

	int itemCounter = 0;
	private JLabel lblTotalItems;

	int 	qtyCounter = 0;

	double dblDiscount = 0.0;
	BigDecimal bdTotalCost;
	BigDecimal bdTaxes;

	//create JLabels
	JLabel lblSubtotal;
	JLabel lblItemPreview;
	JLabel lblTaxes;
	JLabel lblTotalCost;
	JLabel lblQty;

	ArrayList<Order> allorders = new ArrayList<Order>();
	ArrayList quantityArray = new ArrayList();
	ArrayList priceArray = new ArrayList();

	double totalCost;

	double priceLoop = 0.0;

	double tax = 0.079;
	double taxes;

	Employee z;

	ArrayList<Employee> employee_list = new ArrayList<Employee>();

	public static void main(String[] args) {
		TeamProject app = new TeamProject();
		app.setSize(1290,800);
		app.setTitle("Team 4 - Java Lafitte");
		app.setLocationRelativeTo(null);
		app.setVisible(true);
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	//constructor
	TeamProject() {
		Border border4 = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
		orderItemList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		

		// add jsp
		jsp.setBounds(678, 88, 575, 316);
		jsp.setFont(new Font("Monaco", Font.PLAIN, 20));
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setBorder(border4);
		getContentPane().add(jsp);

		employee_list.add(new Employee("411", "123"));
		employee_list.add(new Employee("111", "password"));

	

		boolean found = false;
		int i = 0;
		//login logic
		while (!found) {
			Employee x = employee_list.get(i);
			Employee y = employee_list.get(i+1);
			String id = JOptionPane.showInputDialog("Enter your employee id");
			String pwd = JOptionPane.showInputDialog("Enter your password");
			i++;
			if (i == employee_list.size()) {
				i = 0;
			}
			if ((x.EmployeeID.equals(id) && x.password.equals(pwd))) {
				z = x;
				found = true;
			} else if (y.EmployeeID.equals(id) && y.password.equals(pwd)) {
				z = y;
				found = true;
			} else {
				JOptionPane.showMessageDialog(null, "Invalid login");
				System.out.println("Error, invalid employee id or password");
			}
		}
		//if valid login, open the app
		if (found){
			JOptionPane.showMessageDialog(null, "Welcome Employee " + z.EmployeeID);
		
			System.out.println("PRINT" + z.EmployeeID);

			// add panels
			getContentPane().setLayout(null);

			btnOverride = new JButton("Manager Override");
			btnOverride.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnOverride.setBackground(Color.WHITE);
			btnOverride.setBounds(0, 695, 152, 41);
			btnOverride.setIcon(new ImageIcon(getClass().getResource("/images/Manager override.png")));
			//btnOverride.setBounds(15, 500, 159, 46);
			getContentPane().add(btnOverride);

			Border border3 = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
			JPanel panel = new JPanel();
			panel.setBackground(Color.BLACK);
			panel.setBorder(border3);
			panel.setBounds(165, 88, 489, 316);
			getContentPane().add(panel);
			panel.setLayout(null);

			btnAddItem = new JButton("Add Item");
			btnRemoveItem = new JButton("Remove Item");
			btnEditItem = new JButton("Edit Item");

			btnAddItem.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnAddItem.setBackground(Color.WHITE);
			btnAddItem.setBounds(304, 72, 166, 50);
			btnAddItem.setIcon(new ImageIcon(getClass().getResource("/images/Add Item.png")));
			panel.add(btnAddItem);

			btnEditItem.setBackground(Color.WHITE);
			btnEditItem.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnEditItem.setBounds(304, 128, 166, 50);
			btnEditItem.setIcon(new ImageIcon(getClass().getResource("/images/Edit Item.png")));
			panel.add(btnEditItem);

			btnRemoveItem.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnRemoveItem.setBackground(Color.WHITE);
			btnRemoveItem.setBounds(304, 185, 166, 47);
			btnRemoveItem.setIcon(new ImageIcon(getClass().getResource("/images/Remove Item.png")));
			panel.add(btnRemoveItem);

			btnCoupon = new JButton("Enter Coupon");
			btnCoupon.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnCoupon.setBackground(Color.WHITE);
			btnCoupon.setBounds(0, 650, 152, 41);
			btnCoupon.setIcon(new ImageIcon(getClass().getResource("/images/Coupon.png")));
			//btnCoupon.setBounds(15, 550, 159, 46);
			getContentPane().add(btnCoupon);

			// add items to combo boxes
			Shoes s = new Shoes();
			chooseBrand = new JComboBox(s.brand_options);
			chooseBrand.setBounds(15, 73, 268, 50);
			panel.add(chooseBrand);
			chooseType = new JComboBox(s.type_options);
			chooseType.setBounds(15, 16, 268, 50);
			panel.add(chooseType);

			chooseColor = new JComboBox(s.color_options);
			chooseColor.setBounds(15, 129, 268, 50);
			panel.add(chooseColor);

			chooseSize = new JComboBox(s.size_options);
			chooseSize.setBounds(15, 184, 268, 50);
			panel.add(chooseSize);

			chooseQty = new JComboBox();
			chooseQty.setBounds(15, 239, 268, 50);
			panel.add(chooseQty);
			chooseQty.setEditable(true);

			chooseQty.addItem("Choose Quantity");

			btnPreview = new JButton("Preview Item");
			btnPreview.setBounds(304, 239, 166, 50);
			btnPreview.setBackground(Color.WHITE);
			btnPreview.setIcon(new ImageIcon(getClass().getResource("/images/Preview Item.png")));
			panel.add(btnPreview);

			btnAddItem.addActionListener(this);
			btnEditItem.addActionListener(this);
			btnRemoveItem.addActionListener(this);
			btnCoupon.addActionListener(this);
			btnOverride.addActionListener(this);
			btnPreview.addActionListener(this);

			for(int j = 1; j < 11; j++) {
				chooseQty.addItem(j);
			}
			btnPay = new JButton("Pay");
			btnPay.setBounds(977, 658, 276, 79);
			btnPay.setBackground(Color.WHITE);
			btnPay.setIcon(new ImageIcon(getClass().getResource("/images/Pay.png")));
			getContentPane().add(btnPay);

			//JButton btnPay = new JButton("Place Order");
			btnPay.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnPay.addActionListener(this);
			btnPrint.setBounds(678, 658, 276, 79);
			btnPrint.setBackground(Color.WHITE);
			btnPrint.setIcon(new ImageIcon(getClass().getResource("/images/Print.png")));
			
			getContentPane().add(btnPrint);

			//JButton btnPrint = new JButton("Print");
			btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnPrint.addActionListener(this);

			lblItemPreview = new JLabel("Please make a selection, then click \"Preview Item\"");
			lblItemPreview.setForeground(Color.WHITE);
			lblItemPreview.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblItemPreview.setHorizontalAlignment(SwingConstants.CENTER);
			lblItemPreview.setBounds(165, 418, 489, 318);
			Border border = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
			lblItemPreview.setBorder(border);

			getContentPane().add(lblItemPreview);
			
			Border border2 = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.BLACK);
			panel_1.setBorder(border2);
			panel_1.setBounds(678, 420, 575, 229);
			getContentPane().add(panel_1);
			panel_1.setLayout(null);
			
						lblTotalItems = new JLabel("Total Quantity: ");
						lblTotalItems.setBounds(15, 13, 209, 46);
						panel_1.add(lblTotalItems);
						lblTotalItems.setForeground(Color.WHITE);
						lblTotalItems.setFont(new Font("Tahoma", Font.PLAIN, 30));
						
									lblSubtotal = new JLabel("Subtotal:");
									lblSubtotal.setBounds(15, 66, 155, 46);
									panel_1.add(lblSubtotal);
									lblSubtotal.setForeground(Color.WHITE);
									lblSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 30));
									
												lblTaxes = new JLabel("Taxes:");
												lblTaxes.setBounds(15, 121, 121, 44);
												panel_1.add(lblTaxes);
												lblTaxes.setFont(new Font("Tahoma", Font.PLAIN, 30));
												lblTaxes.setForeground(Color.WHITE);
												
															lblTotalCost = new JLabel("Total Cost:");
															lblTotalCost.setBounds(15, 183, 175, 46);
															panel_1.add(lblTotalCost);
															lblTotalCost.setBackground(Color.BLACK);
															lblTotalCost.setFont(new Font("Tahoma", Font.PLAIN, 30));
															lblTotalCost.setForeground(Color.WHITE);
															
															JSeparator separator = new JSeparator();
															separator.setForeground(Color.WHITE);
															separator.setBounds(0, 57, 575, 2);
															panel_1.add(separator);
															
															JSeparator separator_1 = new JSeparator();
															separator_1.setForeground(Color.WHITE);
															separator_1.setBounds(0, 110, 575, 2);
															panel_1.add(separator_1);
															
															JSeparator separator_2 = new JSeparator();
															separator_2.setForeground(Color.WHITE);
															separator_2.setBounds(0, 163, 575, 2);
															panel_1.add(separator_2);
															
															JSeparator separator_3 = new JSeparator();
															separator_3.setForeground(Color.WHITE);
															separator_3.setBounds(0, 171, 575, 2);
															panel_1.add(separator_3);
															
															lblQty = new JLabel("");
															lblQty.setHorizontalAlignment(SwingConstants.RIGHT);
															lblQty.setFont(new Font("Tahoma", Font.PLAIN, 30));
															lblQty.setForeground(Color.WHITE);
															lblQty.setBounds(461, 15, 99, 42);
															panel_1.add(lblQty);
															
															lblSub = new JLabel("");
															lblSub.setHorizontalAlignment(SwingConstants.RIGHT);
															lblSub.setForeground(Color.WHITE);
															lblSub.setFont(new Font("Tahoma", Font.PLAIN, 30));
															lblSub.setBounds(432, 70, 128, 38);
															panel_1.add(lblSub);
															
															lblTaxi = new JLabel("");
															lblTaxi.setHorizontalAlignment(SwingConstants.RIGHT);
															lblTaxi.setForeground(Color.WHITE);
															lblTaxi.setFont(new Font("Tahoma", Font.PLAIN, 30));
															lblTaxi.setBounds(422, 121, 138, 44);
															panel_1.add(lblTaxi);
															
															lblCosty = new JLabel("");
															lblCosty.setHorizontalAlignment(SwingConstants.RIGHT);
															lblCosty.setFont(new Font("Tahoma", Font.PLAIN, 30));
															lblCosty.setForeground(Color.WHITE);
															lblCosty.setBounds(432, 187, 128, 38);
															panel_1.add(lblCosty);

			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(0, 0, 1269, 773);
			lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/images/UI Final.png")));
			getContentPane().add(lblNewLabel);
		}
	}

	//validate given login
	public void ValidateLogin(String id, String pwd) {
		boolean found = false;
		int i = 0;
		while (!found && i < employee_list.size()) {
			Employee x = employee_list.get(i);
			i++;
			if (x.EmployeeID.equals(id) && x.password.equals(pwd)) {
				found = true;
				JOptionPane.showMessageDialog(null, "Welcome Employee " + x.EmployeeID);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPreview) {
			getShoeImage();
		}

		if (e.getSource() == btnAddItem) {
			// increment the item counter
			qtyCounter += (int) chooseQty.getSelectedItem();

			lblTotalItems.setText("Total Quantity: ");
			lblQty.setText(""+qtyCounter);

			String brand = chooseBrand.getSelectedItem().toString();
			String size = chooseSize.getSelectedItem().toString();
			String type = chooseType.getSelectedItem().toString();
			String color = chooseColor.getSelectedItem().toString();
			int quantity = (int) chooseQty.getSelectedItem();

			quantityArray.add(quantity);
			System.out.println("QTY:" + quantity);

			// calculate price
			taxes = 0.0;

			double price = 0.0;

			price = calculatePrice();

			BigDecimal bdPrice = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_EVEN);

			priceArray.add(bdPrice);
			System.out.println("Price: " + bdPrice);

			priceLoop = 0.0;

			for (int j = 0; j < priceArray.size(); j++) {
				System.out.println("In Array: " + priceArray.get(j));
				BigDecimal bdNew = new BigDecimal(priceArray.get(j).toString());
				String s = bdNew.toString();
				priceLoop += Double.parseDouble(s);
			}

			BigDecimal bdPriceLoop = new BigDecimal(priceLoop).setScale(2, BigDecimal.ROUND_HALF_EVEN);

			lblSubtotal.setText("Subtotal: ");
			lblSub.setText("$"+bdPriceLoop.toString());

			taxes = tax * priceLoop;
			bdTaxes = new BigDecimal(taxes).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			lblTaxes.setText("Taxes: ");
			lblTaxi.setText("$" + bdTaxes);

			totalCost = taxes + priceLoop - dblDiscount;
			bdTotalCost = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			lblTotalCost.setText("Total Cost: ");
			lblCosty.setText("$" + bdTotalCost);

			itemCounter = data.getSize() + 1;
			Order o = new Order(itemCounter, brand, size, type, color, quantity, bdPrice);
			data.addElement(o);

			allorders.add(o);

			// reset the labels
			Shoes s = new Shoes();
			s.ResetLabels();
		}

		if (e.getSource() == btnRemoveItem) {
			int selectedIndex = orderItemList.getSelectedIndex();

			if (selectedIndex >= 0) {

				int currentQty;
				currentQty = (int) quantityArray.get(selectedIndex);
				qtyCounter -= currentQty;
				quantityArray.remove(selectedIndex);

				for (int j = 0; j < quantityArray.size(); j++) {
					System.out.println("Out Array: " + quantityArray.get(j));
				}

				lblTotalItems.setText("Total Quantity: ");
				lblQty.setText(""+qtyCounter);

				BigDecimal currentPrice;

				currentPrice = new BigDecimal(priceArray.get(selectedIndex).toString());
				String strCurrentPrice = currentPrice.toString();
				double dblCurrentPrice = Double.parseDouble(strCurrentPrice);
				System.out.println("CURRENT PRICE:" +currentPrice);

				priceLoop -= dblCurrentPrice;
				priceArray.remove(selectedIndex);

				RecalculateTax();

				BigDecimal bdPriceLoop = new BigDecimal(priceLoop).setScale(2, BigDecimal.ROUND_HALF_EVEN);
				lblSubtotal.setText("Subtotal: ");
				lblSub.setText("$"+bdPriceLoop);
				lblTaxes.setText("Taxes: ");
//				lblTaxi.setText("$"+bdTaxes);
//				lblTotalCost.setText("Total Cost: ");
//				lblCosty.setText("$"+bdTotalCost);

				data.remove(selectedIndex);
				allorders.remove(selectedIndex);


				Order o = new Order(currentQty, strCurrentPrice, strCurrentPrice, strCurrentPrice, strCurrentPrice, currentQty, bdPriceLoop);
				for (int i = 0; i < data.getSize(); i++) {
					int j = 1 + i;
					System.out.println("Item number: " + j);
				}
			}
		}

		if (e.getSource() == btnEditItem) {
			int selectedIndex = orderItemList.getSelectedIndex();

			if (selectedIndex >= 0) {
				if (chooseColor.getSelectedIndex() == 0 || chooseType.getSelectedIndex() == 0
						|| chooseBrand.getSelectedIndex() == 0 || chooseSize.getSelectedIndex() == 0
						|| chooseQty.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Please select new values.");
				} else {
					String sColor = data.getElementAt(selectedIndex).c;
					String sType = data.getElementAt(selectedIndex).t;
					String sBrand = data.getElementAt(selectedIndex).b;
					String sSize = data.getElementAt(selectedIndex).s;
					int iQty = data.getElementAt(selectedIndex).q;

					String newColor = chooseColor.getSelectedItem().toString();
					String newType = chooseType.getSelectedItem().toString();
					String newBrand = chooseBrand.getSelectedItem().toString();
					String newSize = chooseSize.getSelectedItem().toString();
					int newQty = Integer.parseInt(chooseQty.getSelectedItem().toString());

					qtyCounter -= iQty;

					qtyCounter += newQty;

					lblTotalItems.setText("Total Quantity: ");
					lblQty.setText("" + qtyCounter);

					priceArray.remove(selectedIndex);

					double price = 0.0;

					price = calculatePrice();

					BigDecimal bdPrice = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_EVEN);

					priceArray.add(bdPrice);

					Order o = new Order(selectedIndex + 1, newBrand, newSize, newType, newColor, newQty, bdPrice);

					data.setElementAt(o, selectedIndex);

					priceLoop = 0.0;

					for (int j = 0; j < priceArray.size(); j++) {
						System.out.println("In Array: " + priceArray.get(j));
						BigDecimal bdNew = new BigDecimal(priceArray.get(j).toString());
						String s = bdNew.toString();
						priceLoop += Double.parseDouble(s);
					}

					BigDecimal bdPriceLoop = new BigDecimal(priceLoop).setScale(2, BigDecimal.ROUND_HALF_EVEN);

					lblSubtotal.setText("Subtotal: ");
					lblSub.setText("$" + bdPriceLoop.toString());

					taxes = tax * priceLoop;
					BigDecimal bdTaxes = new BigDecimal(taxes).setScale(2, BigDecimal.ROUND_HALF_EVEN);
					lblTaxes.setText("Taxes: ");
					lblTaxi.setText("$" + bdTaxes);

					double totalCost;
					totalCost = taxes + priceLoop - dblDiscount;
					BigDecimal bdTotalCost = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_EVEN);
					lblTotalCost.setText("Total Cost: ");
					lblCosty.setText("$" + bdTotalCost);

					// reset the labels
					Shoes s = new Shoes();
					s.ResetLabels();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please select an item. Change the values and then click the edit button.");
			}
		}

		if (e.getSource() == btnPay) {

			System.out.println("Pay button");
			BigDecimal bdTotalCost;
			bdTotalCost = RecalculateTax();
			bdTotalCost.setScale(2, BigDecimal.ROUND_HALF_EVEN);

			BigDecimal bdAmtPaid = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal bdCurrentPayment = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_EVEN);

			while (Double.parseDouble(bdTotalCost.toString()) > Double.parseDouble(bdAmtPaid.toString())) {
				bdCurrentPayment = promptPayment(bdTotalCost, bdAmtPaid);
				bdAmtPaid = bdCurrentPayment.add(bdAmtPaid);
				System.out.println("AMOUNT PAID " + bdAmtPaid);
				System.out.println("TOTAL COST: " + bdTotalCost);
			}

			Object[] options = new Object[] {"Ok"};
			int n = JOptionPane.showOptionDialog(null, "Congratulations, payment successful.",
					"Payment Successful", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
					null, options, options[0]);

			if (n == JOptionPane.OK_OPTION) {
				// Ok option, remove all items from list
				System.out.println("OK/OK Option");
				data.removeAllElements();
				orderItemList.removeAll();
				quantityArray.clear();
				priceArray.clear();
				priceLoop = 0;
				lblTaxes.setText("Taxes: ");
				lblTotalCost.setText("Total cost: ");
				lblTotalItems.setText("Items: ");
				lblSubtotal.setText("Subtotal: ");
				lblQty.setText("");
				lblSub.setText("");
				lblTaxi.setText("");
				lblCosty.setText("");
			}

		}

		if (e.getSource() == btnPrint) {
			printOrder();
		}

		if (e.getSource() == btnCoupon) {
			dblDiscount = 0.0;
			String coupon = JOptionPane.showInputDialog(null, "Please enter coupon");
			dblDiscount = verifyCoupon(coupon);
			System.out.println(dblDiscount);
		}

		if (e.getSource() == btnOverride) {
			
			blnMgr = z.isManager(z);

			System.out.println(blnMgr);
			//make sure they a manager is logged in to do manager override
			if (blnMgr) {
				double newPrice = 0.0;
				String strnewPrice;
				strnewPrice = JOptionPane.showInputDialog(null, "Please enter price.");
				newPrice = Double.parseDouble(strnewPrice);
				System.out.println(newPrice);

				int selectedIndex = orderItemList.getSelectedIndex();

				if (selectedIndex >= 0) {
					String sColor = data.getElementAt(selectedIndex).c;
					String sType = data.getElementAt(selectedIndex).t;
					String sBrand = data.getElementAt(selectedIndex).b;
					String sSize = data.getElementAt(selectedIndex).s;
					int iQty = data.getElementAt(selectedIndex).q;

					priceArray.remove(selectedIndex);

					double price = newPrice;

					BigDecimal bdPrice = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_EVEN);

					priceArray.add(bdPrice);

					Order o = new Order(selectedIndex + 1, sBrand, sSize, sType, sColor, iQty, bdPrice);

					data.setElementAt(o, selectedIndex);

					priceLoop = 0.0;

					for (int j = 0; j < priceArray.size(); j++) {
						System.out.println("In Array: " + priceArray.get(j));
						BigDecimal bdNew = new BigDecimal(priceArray.get(j).toString());
						String s = bdNew.toString();
						priceLoop += Double.parseDouble(s);
					}

					BigDecimal bdPriceLoop = new BigDecimal(priceLoop).setScale(2, BigDecimal.ROUND_HALF_EVEN);

					lblSubtotal.setText("Subtotal: $" + bdPriceLoop.toString());

					taxes = tax * priceLoop;
					BigDecimal bdTaxes = new BigDecimal(taxes).setScale(2, BigDecimal.ROUND_HALF_EVEN);
					lblTaxes.setText("Taxes: $" + bdTaxes);

					double totalCost;
					totalCost = taxes + priceLoop - dblDiscount;
					BigDecimal bdTotalCost = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_EVEN);
					lblTotalCost.setText("Total Cost: $" + bdTotalCost);

					// reset the labels
					Shoes s = new Shoes();
					s.ResetLabels();
				}

			} else {
				//if a manager is not logged in, prompt the employee to have a manager log in
				JOptionPane.showMessageDialog(null, "Please have a manager log in.");

				boolean found = false;
				int i = 0;

				String id = JOptionPane.showInputDialog("Enter your manager id");
				String pwd = JOptionPane.showInputDialog("Enter your manager password");

				while (!found && (i < employee_list.size())) {
					Employee x = employee_list.get(i);
					System.out.println("SECOND WHILE");
					if (x.EmployeeID.equals(id) && x.password.equals(pwd)) {
						found = true;
						System.out.println("IF");
						if (x.isManager(z)) {
							System.out.println("SECOND IF");

							double newPrice = 0.0;
							String strnewPrice;
							strnewPrice = JOptionPane.showInputDialog(null, "Please enter price.");
							newPrice = Double.parseDouble(strnewPrice);
							System.out.println(newPrice);
						}
						
						JOptionPane.showMessageDialog(null, "Welcome Employee " + z.EmployeeID);
						z = x;
						String yEmpId = z.EmployeeID;
						System.out.println("PRINT" + yEmpId);
					}
					i++;
				}
			}
		}
	}

	public void getShoeImage() {

		String cbBrand = chooseBrand.getSelectedItem().toString();
		String cbColor = chooseColor.getSelectedItem().toString();

		if (cbBrand == "Nike" && cbColor == "Red") {
			// create icon for athletic shoe
			Image athleticImage = Toolkit.getDefaultToolkit().getImage("Nike - red.png");
			athleticImage.getScaledInstance(200, 200, 0);
			ImageIcon athleticIcon = new ImageIcon(athleticImage);
			lblItemPreview.setIcon(athleticIcon);
		}

		if (cbBrand == "Nike" && cbColor == "Blue") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Nike - blue.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Nike" && cbColor == "Purple") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Nike - purple.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Nike" && cbColor == "Pink") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Nike - pink.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Adidas" && cbColor == "Red") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Adidas - red.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Adidas" && cbColor == "Blue") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Adidas - blue.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Adidas" && cbColor == "Purple") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Adidas - purple.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Adidas" && cbColor == "Pink") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Adidas - pink.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Asics" && cbColor == "Red") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Asics - red.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Asics" && cbColor == "Blue") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Asics - blue.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Asics" && cbColor == "Purple") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Asics - purple.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Asics" && cbColor == "Pink") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Asics - pink.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Zara" && cbColor == "Red") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Zara - red.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Zara" && cbColor == "Blue") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Zara - blue.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Zara" && cbColor == "Purple") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Zara - purple.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Zara" && cbColor == "Pink") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Zara - pink.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Steve Madden" && cbColor == "Red") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Steve madden - red.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Steve Madden" && cbColor == "Blue") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Steve madden - blue.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Steve Madden" && cbColor == "Purple") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Steve madden - purple.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Steve Madden" && cbColor == "Pink") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Steve madden - pink.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Sperry's" && cbColor == "Red") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Sperrys - red.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Sperry's" && cbColor == "Blue") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Sperrys - blue.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Sperry's" && cbColor == "Purple") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Sperrys - purple.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
		
		if (cbBrand == "Sperry's" && cbColor == "Pink") {
			// create icon for dress shoe
			Image dressyImage = Toolkit.getDefaultToolkit().getImage("Sperrys - pink.png");
			ImageIcon dressyIcon = new ImageIcon(dressyImage);
			lblItemPreview.setIcon(dressyIcon);
		}
	}

	public double calculatePrice() {
		double price = 5.0;

		String size = chooseSize.getSelectedItem().toString();
		String type = chooseType.getSelectedItem().toString();
		int quantity = (int) chooseQty.getSelectedItem();

		if (type == "Dress") {
			price += 2.0;
		} else if (type == "Athletic") {
			price += 1.0;
		}

		if (size == "5") {

		} else {
			price *= ((Double.parseDouble(size) - 5.0) * 1.15);
		}

		price *= quantity;

		return price;

	}

	Boolean blnMgr = false;
	private JLabel lblSub;
	private JLabel lblTaxi;
	private JLabel lblCosty;

	public BigDecimal promptPayment(BigDecimal bdTotalCost, BigDecimal bdAmtPaid) {

		String strAmtPaid;

		BigDecimal bdAmtRemaining = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_EVEN);
		bdAmtRemaining = bdTotalCost.subtract(bdAmtPaid);

		// create icon for money
		Image moneyIMG = Toolkit.getDefaultToolkit().getImage("moneybag.png");
		ImageIcon moneyIcon = new ImageIcon(moneyIMG);

		// create icon for credit card
		Image ccIMG = Toolkit.getDefaultToolkit().getImage("creditcard.png");
		ImageIcon creditCardIcon = new ImageIcon(ccIMG);

		// create icon for check
		Image checkIMG = Toolkit.getDefaultToolkit().getImage("check.png");
		ImageIcon checkIcon = new ImageIcon(checkIMG);

		Object[] options = new Object[] {"Cash", "Credit Card", "Check"};
		int n = JOptionPane.showOptionDialog(null, "Select payment method",
				"Payment Method", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);

		if (n == JOptionPane.YES_OPTION) {
			// cash option
			System.out.println("Cash");

			strAmtPaid = (String) JOptionPane.showInputDialog(null, "How much will you pay in cash? You have a balance of: $" + bdAmtRemaining,
					"Cash Payment", JOptionPane.QUESTION_MESSAGE, moneyIcon, null, null);
			bdAmtPaid = new BigDecimal(strAmtPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			System.out.println(bdAmtPaid);
			return bdAmtPaid;
		}

		if (n == JOptionPane.NO_OPTION) {
			// credit card option
			System.out.println("Credit Card");
			strAmtPaid = (String) JOptionPane.showInputDialog(null, "How much will you pay by credit card? You have a balance of: $" + bdAmtRemaining,
					"Credit Card Payment", JOptionPane.QUESTION_MESSAGE, creditCardIcon, null, null);
			String ccNum = JOptionPane.showInputDialog("Please enter credit card number");
			bdAmtPaid = new BigDecimal(strAmtPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			System.out.println(bdAmtPaid);
			return bdAmtPaid;
		}

		if (n == JOptionPane.CANCEL_OPTION) {
			// check option
			System.out.println("Check");
			strAmtPaid = (String) JOptionPane.showInputDialog(null, "How much will you pay by check? You have a balance of: $" + bdAmtRemaining,
					"Check Payment", JOptionPane.QUESTION_MESSAGE, checkIcon, null, null);
			String check = JOptionPane.showInputDialog("Please enter check number");
			bdAmtPaid = new BigDecimal(strAmtPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			System.out.println(bdAmtPaid);
			return bdAmtPaid;
		}
		return bdAmtPaid;
	}

	public void printOrder() {

		// generate on screen receipt:
		ReceiptPanel p = new ReceiptPanel();
		JFrame f = new JFrame();
		f.setSize(new Dimension(600,200+ data.size() * 5));
		f.setLocationRelativeTo(null);
		f.getContentPane().add(p);
		f.setTitle("Receipt");
		f.setVisible(true);

		// submit print job:
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(p);
		if (job.printDialog()) {
			try {
				job.print();
			} catch(PrinterException x_x) {
				System.out.println("Error printing: " + x_x);
			}
		}
	}

	class ReceiptPanel extends JPanel implements Printable {

		Font f = new Font("Tahoma", Font.PLAIN, 15);
		Image receiptIMG = Toolkit.getDefaultToolkit().getImage("receiptheader.png");
		ImageIcon receiptIMGICON = new ImageIcon(receiptIMG);

		
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);

			this.setLayout(null);

			// make it smooth:
			Graphics2D g2d = (Graphics2D) graphics;
			RenderingHints hints = new RenderingHints(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			g2d.setRenderingHints(hints);

			// generate header
			JLabel title = new JLabel();
			title.setIcon(receiptIMGICON);
			title.setBounds(10, 0, 600, 60);
			add(title);

			// list of items in the cart
			JLabel[] labels = new JLabel[data.size()];
			for (int i = 0; i < data.size(); i++) {
				labels[i] = new JLabel("" + (i + 1) + ". " + data.get(i));
				labels[i].setFont(f);
				labels[i].setBounds(10, 75+(i*18), 600, 18);
				add(labels[i]);
			}

			BigDecimal bdTotalCost = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_EVEN);

			// total cost in big decimal
			g2d.setFont(new Font("Tahoma", Font.PLAIN, 18));
			g2d.drawString("Total: $" + bdTotalCost.toString(), getWidth()-125, getHeight()-30);

			// what are those slogan
			g2d.setFont(f.deriveFont(Font.ITALIC, 20));
			g2d.drawString("Shoes are news", getWidth()-145, getHeight()-10);
		}

		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			if (pageIndex > 0) {
				return(NO_SUCH_PAGE);
			} else {
				Graphics2D g2d = (Graphics2D) graphics;
				g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
				paint(g2d);
				return(PAGE_EXISTS);
			}
		}
	}

	public BigDecimal RecalculateTax() {

		taxes = tax * priceLoop;
		BigDecimal bdTaxes = new BigDecimal(taxes).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		lblTaxes.setText("Taxes: $" + bdTaxes);

		totalCost = taxes + priceLoop - dblDiscount;
		BigDecimal bdTotalCost = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		//lblTotalCost.setText("Total Cost: $" + bdTotalCost);
		lblTaxi.setText("$"+bdTaxes);
		lblTotalCost.setText("Total Cost: ");
		lblCosty.setText("$"+bdTotalCost);
		return bdTotalCost;
	}

	public class Shoes {

		int quantity;
		double itemPrice;

		String size_options[] = {"Shoe Size", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5",
				"9", "9.5", "10", "10.5", "11", "11.5", "12", "12.5", "13", "13.5", "14",
				"14.5", "15", "15.5", "16", "16.5", "17", "17.5", "18", "18.5", "19", "19.5", "20"};
		String type_options[] = {"Shoe Type", "Athletic", "Dress", "Casual"};
		String atheltic_options[] = {"Shoe Brand", "Nike"};
		String dress_options[] = {"Shoe Brand", "Steve Madden"};
		String casual_options[] = {"Shoe Brand", "Sperry's", "Crocs"};
		String brand_options[] = {"Shoe Brand", "Nike", "Adidas", "Asics", "Zara", "Steve Madden", "Sperry's"};
		String color_options[] = {"Shoe Color", "Red", "Blue", "Purple", "Pink", "Green", "Yellow", "White", "Gray"};

		void ResetLabels() {
			chooseBrand.setSelectedIndex(0);
			chooseType.setSelectedIndex(0);
			chooseColor.setSelectedIndex(0);
			chooseSize.setSelectedIndex(0);
			chooseQty.setSelectedIndex(0);
		}
	}

	public class Order {
		String b, s, t, c;
		int q, x;
		BigDecimal p;

		Order(int itemCounter, String brand, String size, String type, String color, int quantity, BigDecimal price) {
			x = itemCounter;
			b = brand;
			s = size;
			t = type;
			c = color;
			q = quantity;
			p = price;
		}

		public String toString() {
			return "Item Number " + x + ": " + q + " pair(s) of size " + s + " " + c + " " + t + " " + b + " shoes at a price of $" + p + ".";
		}
	}

	double verifyCoupon (String coupon) {
		dblDiscount = 0.0;
		if(coupon.length() == 8) {
			coupon = coupon.toUpperCase();
			for(int i = 0; i<coupon.length(); i++) {
				if(coupon.charAt(i) == 'S') {
					dblDiscount +=1;
				}
			}
			BigDecimal bdDiscount = new BigDecimal(dblDiscount).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			JOptionPane.showMessageDialog(null, "You saved: $" + bdDiscount);
		}
		return dblDiscount;
	}

	public class Employee {
		String EmployeeID;
		String password;

		Employee(String id, String p) {
			EmployeeID = id;
			password = p;
		}

		public boolean isManager(Employee x) {

			if(x.EmployeeID.startsWith("4")) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}


