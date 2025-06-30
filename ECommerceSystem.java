import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ECommerceSystem extends JFrame {

    // Product class
    class Product {
        int id;
        String name;
        double price;

        Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String toString() {
            return name + " - ₹" + price;
        }
    }

    // Cart class
    class Cart {
        ArrayList<Product> items = new ArrayList<>();

        void addProduct(Product p) {
            items.add(p);
        }

        ArrayList<Product> getItems() {
            return items;
        }

        double getTotal() {
            double total = 0;
            for (Product p : items) {
                total += p.price;
            }
            return total;
        }

        void clearCart() {
            items.clear();
        }
    }

    // GUI components
    DefaultListModel<Product> productModel = new DefaultListModel<>();
    DefaultListModel<Product> cartModel = new DefaultListModel<>();
    Cart cart = new Cart();

    public ECommerceSystem() {
        setTitle("E-Commerce Management System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel lblTitle = new JLabel("Welcome to My Online Store");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(230, 10, 400, 30);
        add(lblTitle);

        JLabel lblProducts = new JLabel("Products");
        lblProducts.setFont(new Font("Verdana", Font.BOLD, 16));
        lblProducts.setBounds(50, 50, 200, 25);
        add(lblProducts);

        JList<Product> listProducts = new JList<>(productModel);
        listProducts.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listProducts.setBackground(new Color(255, 255, 204));
        JScrollPane scroll1 = new JScrollPane(listProducts);
        scroll1.setBounds(50, 80, 250, 250);
        scroll1.setBorder(new LineBorder(Color.GRAY));
        add(scroll1);

        JButton btnAddToCart = new JButton("Add to Cart");
        btnAddToCart.setBackground(new Color(51, 153, 255));
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setFont(new Font("Arial", Font.BOLD, 14));
        btnAddToCart.setBounds(310, 160, 150, 35);
        add(btnAddToCart);

        JLabel lblCart = new JLabel("Shopping Cart");
        lblCart.setFont(new Font("Verdana", Font.BOLD, 16));
        lblCart.setBounds(500, 50, 200, 25);
        add(lblCart);

        JList<Product> listCart = new JList<>(cartModel);
        listCart.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listCart.setBackground(new Color(204, 255, 204));
        JScrollPane scroll2 = new JScrollPane(listCart);
        scroll2.setBounds(500, 80, 250, 250);
        scroll2.setBorder(new LineBorder(Color.GRAY));
        add(scroll2);

        JLabel lblTotal = new JLabel("Total: ₹0.00");
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTotal.setForeground(Color.BLACK);
        lblTotal.setBounds(500, 340, 200, 30);
        add(lblTotal);

        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.setBackground(new Color(0, 204, 102));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 14));
        btnCheckout.setBounds(640, 340, 110, 35);
        add(btnCheckout);

        // Sample products
        productModel.addElement(new Product(1, "Laptop", 74999));
        productModel.addElement(new Product(2, "Smartphone", 25999));
        productModel.addElement(new Product(3, "Headphones", 1999));
        productModel.addElement(new Product(4, "Smartwatch", 5499));
        productModel.addElement(new Product(5, "Tablet", 15999));

        // Button Actions
        btnAddToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selected = listProducts.getSelectedValue();
                if (selected != null) {
                    cart.addProduct(selected);
                    cartModel.addElement(selected);
                    lblTotal.setText("Total: ₹" + cart.getTotal());
                } else {
                    JOptionPane.showMessageDialog(ECommerceSystem.this, "Please select a product to add to cart.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.getItems().isEmpty()) {
                    JOptionPane.showMessageDialog(ECommerceSystem.this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ECommerceSystem.this,
                            "Thank you for your purchase!\nTotal Amount: ₹" + cart.getTotal(),
                            "Order Placed", JOptionPane.INFORMATION_MESSAGE);
                    cart.clearCart();
                    cartModel.clear();
                    lblTotal.setText("Total: ₹0.00");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ECommerceSystem();
    }
}
