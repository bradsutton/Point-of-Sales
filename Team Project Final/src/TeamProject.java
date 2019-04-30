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



@SuppressWarnings("serial")
//building the application frame and implementing the action listener for button-click events
public class TeamProject extends JFrame implements ActionListener {

	final JFrame frame = new JFrame("Display Mode");

	// creating the list for orders to be added to, edited from, and removed from
	DefaultListModel<Order> data = new DefaultListModel<Order>();
	JList<Order> orderItemList = new JList<Order>(data);
	JScrollPane jsp = new JScrollPane(orderItemList);

	//creating buttons on the interface
	JButton btnAddItem, btnRemoveItem, btnEditItem, btnPay, btnPreview;
	JButton btnPrint = new JButton("Print");
	JButton btnCoupon, btnOverride;

	//creating combo boxes on the interface
	JComboBox chooseBrand, chooseColor, chooseSize, chooseQty;
	JComboBox<String> chooseType = new JComboBox<String>();

	//integer variable and label created to count number of items in the JList when shoes are added or removed by user
	int itemCounter = 0;
	private JLabel lblTotalItems;

	int qtyCounter = 0;

	double dblDiscount = 0.0;
	BigDecimal bdTotalCost;
	BigDecimal bdTaxes;

	//create JLabels
	JLabel lblSubtotal;
	JLabel lblItemPreview;
	JLabel lblTaxes;
	JLabel lblTotalCost;
	JLabel lblQty;

	//arraylists to store orders, quantities, and price as shoes are added or removed
	ArrayList<Order> allorders = new ArrayList<Order>();
	ArrayList quantityArray = new ArrayList();
	ArrayList priceArray = new ArrayList();

	//double variables used later for looping
	double totalCost;
	double priceLoop = 0.0;
	double tax = 0.079;
	double taxes;

	// used later for employee login verification
	Employee z;
	ArrayList<Employee> employee_list = new ArrayList<Employee>();

	//creates the user interface, sets the dimensions, and establishes the close operation
	public static void main(String[] args) {
		TeamProject app = new TeamProject();
		app.setSize(1290,800);
		app.setTitle("Team 4 - Java Lafitte");
		app.setLocationRelativeTo(null);
		app.setVisible(true);
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	//constructor begins
	TeamProject() {
		
		// creates the JList that orders will be added to and removed from and adds design
		Border border4 = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
		orderItemList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		

		// adds the scrollpane to the order list and adds design
		jsp.setBounds(678, 88, 575, 316);
		jsp.setFont(new Font("Monaco", Font.PLAIN, 20));
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setBorder(border4);
		getContentPane().add(jsp);

		// storing login usernames and passwords in an arraylist 
		// one login is for employee, the other is for manager
		// plays a key role for the manager override button
		employee_list.add(new Employee("411", "123"));
		employee_list.add(new Employee("111", "password"));

	
		// login logic using a while loop
		boolean logged_in = false;
		int i = 0;
	
		while (!logged_in) {
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
				logged_in = true;
			} else if (y.EmployeeID.equals(id) && y.password.equals(pwd)) {
				z = y;
				logged_in = true;
			} else {
				JOptionPane.showMessageDialog(null, "Invalid login");
				System.out.println("Error, invalid employee id or password");
			}
		}
		//if login is valid, the app will open
		if (logged_in){
			
			// message box confirming login and employee ID 
			JOptionPane.showMessageDialog(null, "Welcome Employee " + z.EmployeeID);
		
			System.out.println("PRINT" + z.EmployeeID);

			// add panels and designs to the user interface
			getContentPane().setLayout(null);

			// creates manager override button, sets bounds, and adds design from the images folder, adds button to UI
			btnOverride = new JButton("Manager Override");
			btnOverride.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnOverride.setBackground(Color.WHITE);
			btnOverride.setBounds(0, 695, 152, 41);
			btnOverride.setIcon(new ImageIcon(getClass().getResource("/images/Manager override.png")));
			getContentPane().add(btnOverride);

			// creates the panel that houses the combo boxes and add/remove/edit/preview buttons
			Border border3 = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
			JPanel panel = new JPanel();
			panel.setBackground(Color.BLACK);
			panel.setBorder(border3);
			panel.setBounds(165, 88, 489, 316);
			getContentPane().add(panel);
			panel.setLayout(null);

			// adding the buttons to the panel, setting the bounds, and adding the design from images folder
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
			getContentPane().add(btnCoupon);

			// this is the beginning of the order instance, selection choices are added to the combo boxes
			// combo boxes are then added to the panel
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

			// action listeners are added to each button, specifically listening for the click event by user
			btnAddItem.addActionListener(this);
			btnEditItem.addActionListener(this);
			btnRemoveItem.addActionListener(this);
			btnCoupon.addActionListener(this);
			btnOverride.addActionListener(this);
			btnPreview.addActionListener(this);

			// for loop to add the quantity selections to the quantity combo box
			// user may only select a quantity of up to 10 for a specific shoe
			for(int j = 1; j < 11; j++) {
				chooseQty.addItem(j);
			}
			
			// adding the pay and print buttons to the interface, and adding the action listeners to the buttons 
			// also adding the designs to the buttons
			btnPay = new JButton("Pay");
			btnPay.setBounds(977, 658, 276, 79);
			btnPay.setBackground(Color.WHITE);
			btnPay.setIcon(new ImageIcon(getClass().getResource("/images/Pay.png")));
			getContentPane().add(btnPay);
			btnPay.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnPay.addActionListener(this);
			
			btnPrint.setBounds(678, 658, 276, 79);
			btnPrint.setBackground(Color.WHITE);
			btnPrint.setIcon(new ImageIcon(getClass().getResource("/images/Print.png")));
			getContentPane().add(btnPrint);
			btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnPrint.addActionListener(this);

			// creating the preview window for images to load 
			lblItemPreview = new JLabel("Please make a selection, then click \"Preview Item\"");
			lblItemPreview.setForeground(Color.WHITE);
			lblItemPreview.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblItemPreview.setHorizontalAlignment(SwingConstants.CENTER);
			lblItemPreview.setBounds(165, 418, 489, 318);
			Border border = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
			lblItemPreview.setBorder(border);
			getContentPane().add(lblItemPreview);
			
			//creating a second panel to house quantity, subtotal, tax, and total cost labels
			Border border2 = BorderFactory.createLineBorder(Color.decode("#F18D1B"), 2);
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.BLACK);
			panel_1.setBorder(border2);
			panel_1.setBounds(678, 420, 575, 229);
			getContentPane().add(panel_1);
			panel_1.setLayout(null);
			
			//labels are added to the second panel
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
															
															//separators to give the second panel an itemized look
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
															
															/* the following labels will hold the actual quantity
															   amount and dollar values for subtotal, taxes and
															   total cost as shoes are added, edited or removed from 
															   the order list
															*/
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

			// adds the entire background to the application
			// basically shows the design of the entire user interface
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(0, 0, 1269, 773);
			lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/images/UI Final.png")));
			getContentPane().add(lblNewLabel);
		}
	}

	//validate given login
	/*public void ValidateLogin(String id, String pwd) {
		boolean logged_in = false;
		int i = 0;
		while (!logged_in && i < employee_list.size()) {
			Employee x = employee_list.get(i);
			i++;
			if (x.EmployeeID.equals(id) && x.password.equals(pwd)) {
				logged_in = true;
				JOptionPane.showMessageDialog(null, "Welcome Employee " + x.EmployeeID);
			}
		}
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// when the preview button is clicked
		if (e.getSource() == btnPreview) {
		// calling the method that shows user a picture of the shoe selection
			getShoeImage();
		}
		
		// when the add item button is clicked 
		if (e.getSource() == btnAddItem) {
		// incrementing the item counter by casting the selected item in the quantity combo box to an integer
			qtyCounter += (int) chooseQty.getSelectedItem();
		// quantity label updates as shoes are added
			lblTotalItems.setText("Total Quantity: ");
			lblQty.setText(""+qtyCounter);
		
		// converting selected items in each combo box to a string 
		// 
			String brand = chooseBrand.getSelectedItem().toString();
			String size = chooseSize.getSelectedItem().toString();
			String type = chooseType.getSelectedItem().toString();
			String color = chooseColor.getSelectedItem().toString();
			int quantity = (int) chooseQty.getSelectedItem();

			quantityArray.add(quantity);
			System.out.println("QTY:" + quantity);

			// variables that will change as shoes are added
			taxes = 0.0;
			double price = 0.0;

			// calling the method that calculates price
			price = calculatePrice();

			// converting the price double data type to a Big Decimal data type rounded to 2 decimal places
			BigDecimal bdPrice = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_EVEN);

			// adding the price to an array 
			priceArray.add(bdPrice);
			System.out.println("Price: " + bdPrice);

			// resetting the price loop in case more shoes are added
			priceLoop = 0.0;

			// for loop to calculate price as more shoes are added
			for (int j = 0; j < priceArray.size(); j++) {
				System.out.println("In Array: " + priceArray.get(j));
				BigDecimal bdNew = new BigDecimal(priceArray.get(j).toString());
				String s = bdNew.toString();
				priceLoop += Double.parseDouble(s);
			}

			// the double data type priceLoop holds the incremented price as more shoes are added
			// converting that double data type into a big decimal to ensure accurate rounding to 2 decimal places
			BigDecimal bdPriceLoop = new BigDecimal(priceLoop).setScale(2, BigDecimal.ROUND_HALF_EVEN);

			// when shoes are added, subtotal label updates with the bdPriceLoop converted to a string
			lblSubtotal.setText("Subtotal: ");
			lblSub.setText("$"+bdPriceLoop.toString());

			// calculating tax based on price
			taxes = tax * priceLoop;
			bdTaxes = new BigDecimal(taxes).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			lblTaxes.setText("Taxes: ");
			lblTaxi.setText("$" + bdTaxes);

			// calculating total cost with or without discount applied
			totalCost = taxes + priceLoop - dblDiscount;
			bdTotalCost = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			lblTotalCost.setText("Total Cost: ");
			lblCosty.setText("$" + bdTotalCost);

			// counting the number of line items in the list as shoes are added
			itemCounter = data.getSize() + 1;
			
			// after calculations, this is what displays each selection in the list
			Order o = new Order(itemCounter, brand, size, type, color, quantity, bdPrice);
			data.addElement(o);

			// adding the order to the orders array
			allorders.add(o);

			// combo boxes are reset, allowing more selections to be made in the same session
			Shoes s = new Shoes();
			// resetting the labels
			s.ResetLabels();
		}

			// when the remove item button is clicked by the user
		if (e.getSource() == btnRemoveItem) {
			
			// user must select the order in the list before clicking remove item
			int selectedIndex = orderItemList.getSelectedIndex();

			if (selectedIndex >= 0) {

			// updating the quantity when the order is removed from the list
				int currentQty;
				currentQty = (int) quantityArray.get(selectedIndex);
				qtyCounter -= currentQty;
				quantityArray.remove(selectedIndex);
			
			// printing to the console to test accuracy of the new quantity
				for (int j = 0; j < quantityArray.size(); j++) {
					System.out.println("Out Array: " + quantityArray.get(j));
				}
			// updating the labels that the user sees
				lblTotalItems.setText("Total Quantity: ");
				lblQty.setText(""+qtyCounter);

				BigDecimal currentPrice;
			// 
				currentPrice = new BigDecimal(priceArray.get(selectedIndex).toString());
				String strCurrentPrice = currentPrice.toString();
				double dblCurrentPrice = Double.parseDouble(strCurrentPrice);
				System.out.println("CURRENT PRICE:" +currentPrice);

				priceLoop -= dblCurrentPrice;
				priceArray.remove(selectedIndex);

			// calling the method to recalculate the tax 
				RecalculateTax();
				
			// updating the price that the user will see
				BigDecimal bdPriceLoop = new BigDecimal(priceLoop).setScale(2, BigDecimal.ROUND_HALF_EVEN);
				lblSubtotal.setText("Subtotal: ");
				lblSub.setText("$"+bdPriceLoop);
				lblTaxes.setText("Taxes: ");

			// removes the selected order from the list and from the array
				data.remove(selectedIndex);
				allorders.remove(selectedIndex);

			// new order 
				Order o = new Order(currentQty, strCurrentPrice, strCurrentPrice, strCurrentPrice, strCurrentPrice, currentQty, bdPriceLoop);
			}
		}
		
		// when the user clicks the edit item button
		if (e.getSource() == btnEditItem) {
			int selectedIndex = orderItemList.getSelectedIndex();

			// begin nested if statement
			if (selectedIndex >= 0) {
				
				// message box prompting the user to select new values if they haven't already done so
				if (chooseColor.getSelectedIndex() == 0 || chooseType.getSelectedIndex() == 0
						|| chooseBrand.getSelectedIndex() == 0 || chooseSize.getSelectedIndex() == 0
						|| chooseQty.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Please select new values.");
				
				// when the user has selected new values 
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

					// updating all of the line items 
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

					// reset the combo boxes and labels
					Shoes s = new Shoes();
					s.ResetLabels();
				}

				// when the user does not select the order in the list
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

		// when the user clicks the print button
		if (e.getSource() == btnPrint) {
		// calling the method that will print the order	
			printOrder();
		}

		if (e.getSource() == btnCoupon) {
			dblDiscount = 0.0;
			String coupon = JOptionPane.showInputDialog(null, "Please enter coupon");
			dblDiscount = verifyCoupon(coupon);
			System.out.println(dblDiscount);
		}

		// when the user clicks the override button
		// won't get involved in this during the presentation, wasn't heavily involved 
		if (e.getSource() == btnOverride) {
			
			blnMgr = z.isManager(z);

			System.out.println(blnMgr);
			//make sure the manager is logged in to do manager override
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

				boolean logged_in = false;
				int i = 0;

				String id = JOptionPane.showInputDialog("Enter your manager id");
				String pwd = JOptionPane.showInputDialog("Enter your manager password");

				while (!logged_in && (i < employee_list.size())) {
					Employee x = employee_list.get(i);
					System.out.println("SECOND WHILE");
					if (x.EmployeeID.equals(id) && x.password.equals(pwd)) {
						logged_in = true;
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

	
	// my favorite method that I worked on 
	// combination of the shoe brand and color selected dictates the picture shown in the preview window
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

	// method to calculate price
	// this method is called each time user adds, removes, or edits a shoe
	// price determined by type, size, and quantity
	public double calculatePrice() {
		double price = 5.0;

		String size = chooseSize.getSelectedItem().toString();
		String type = chooseType.getSelectedItem().toString();
		int quantity = (int) chooseQty.getSelectedItem();

		if (type == "Dress") {
			price += 80.0;
		} else if (type == "Athletic") {
			price += 70.0;
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

		// this allows the user to select the payment method 
		Object[] options = new Object[] {"Cash", "Credit Card", "Check"};
		int n = JOptionPane.showOptionDialog(null, "Select payment method",
				"Payment Method", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);

		if (n == JOptionPane.YES_OPTION) {
		// cash option
			System.out.println("Cash");

		// user inputs how much he/she wants to pay in cash
		// converted from a string to a big decimal rounded to 2 decimal places
			strAmtPaid = (String) JOptionPane.showInputDialog(null, "How much will you pay in cash? You have a balance of: $" + bdAmtRemaining,
					"Cash Payment", JOptionPane.QUESTION_MESSAGE, moneyIcon, null, null);
			bdAmtPaid = new BigDecimal(strAmtPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			System.out.println(bdAmtPaid);
			return bdAmtPaid;
		}

		if (n == JOptionPane.NO_OPTION) {
		// credit card option
			System.out.println("Credit Card");
			
		// user inputs how much he/she wants to pay by credit card
		// converted from a string to a big decimal rounded to 2 decimal places
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
			
		// user inputs how much he/she wants to pay by credit card
		// converted from a string to a big decimal rounded to 2 decimal places
			strAmtPaid = (String) JOptionPane.showInputDialog(null, "How much will you pay by check? You have a balance of: $" + bdAmtRemaining,
					"Check Payment", JOptionPane.QUESTION_MESSAGE, checkIcon, null, null);
			String check = JOptionPane.showInputDialog("Please enter check number");
			bdAmtPaid = new BigDecimal(strAmtPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			System.out.println(bdAmtPaid);
			return bdAmtPaid;
		}
		return bdAmtPaid;
	}

	// method that is called when the print button is clicked
	public void printOrder() {

	// generate on screen receipt:
		ReceiptPanel p = new ReceiptPanel();
		JFrame f = new JFrame();
		f.setSize(new Dimension(600,200+ data.size() * 5));
		f.setLocationRelativeTo(null);
		f.getContentPane().add(p);
		f.setTitle("Receipt");
		f.setVisible(true);

	// submitting the print job using a try/catch block to catch 
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

	// this displays the graphics and orders on the receipt when print button is clicked
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

	// method that is called to recalculate tax when user adds/edits/removes items from the list
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

		// all of the options that go into each combo box
		String size_options[] = {"Shoe Size", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5",
				"9", "9.5", "10", "10.5", "11", "11.5", "12", "12.5", "13", "13.5", "14",
				"14.5", "15", "15.5", "16", "16.5", "17", "17.5", "18", "18.5", "19", "19.5", "20"};
		String type_options[] = {"Shoe Type", "Athletic", "Dress", "Casual"};
		String atheltic_options[] = {"Shoe Brand", "Nike"};
		String dress_options[] = {"Shoe Brand", "Steve Madden"};
		String casual_options[] = {"Shoe Brand", "Sperry's", "Crocs"};
		String brand_options[] = {"Shoe Brand", "Nike", "Adidas", "Asics", "Zara", "Steve Madden", "Sperry's"};
		String color_options[] = {"Shoe Color", "Red", "Blue", "Purple", "Pink", "Green", "Yellow", "White", "Gray"};

		// resets combo boxes when orders are added/edited/removed
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

	// format used when adding orders to the order list
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

	// employee class used to determine if manager or regular employee is logged in
	// enables functionality of the manager override button
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


