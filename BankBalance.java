import java.util.*;
import java.io.*;
public class BankBalance {
    
    static Scanner keyb = new Scanner(System.in);
    static Double OPENING_BALANCE = 0.0;
    static Double END_BALANCE = 0.0;
    
    public static void main(String[] args) {
        System.out.println("Welcom to the Banking App");
        OPENING_BALANCE = getOpeningBalance();
        
        ArrayList<Double> transactions = new ArrayList<Double>();
        if (args.length > 0) {
            String filename = args[0];
            transactions = readExistingTransactions(filename);
        } else {
            transactions = getUserTransactions();
        }
        
        displayReports(transactions);
        displayReports(transactions, "statement.out");
        
        System.out.println("\nThanks for using the Banking App");
    }
    
    // OPENING_BALANCE = getOpeningBalance();
    static double getOpeningBalance() {
        System.out.print("What is the opening balance? ");
        double openingBalance = 0.0;
        while (openingBalance <= 0) {
            openingBalance = keyb.nextDouble();
            if (openingBalance <= 0) {
                System.out.println("Opening balance must be greater than zero");
                System.out.print("What is the opening balance? ");
            }
        }
        return openingBalance;
    }
    
    // ArrayList<Double transactions = readExistingTransactions(filename);
    static ArrayList<Double> readExistingTransactions(String filename) {
        ArrayList<Double> transactions = new ArrayList<Double>();
        END_BALANCE = OPENING_BALANCE;
        double transaction = -1.0;
        try (Scanner fileScan = new Scanner(new File(filename))) {
            while (fileScan.hasNext()) {
                transaction = Double.parseDouble(fileScan.nextLine());
                END_BALANCE += transaction;
                if (END_BALANCE < 0) {
                    // System.out.println("Insufficient balance, withdrawal denied");
                    END_BALANCE -= transaction;
                } else {
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    // ArrayList<Double> transactions = getUserTransactions();
    static ArrayList<Double> getUserTransactions() {
        ArrayList<Double> transactions = new ArrayList<Double>();
        END_BALANCE = OPENING_BALANCE;
        System.out.println("Enter the transaction amounts");
        System.out.println("(positive for deposits, negative for withdrawals, zero to stop) :");
        double transaction = -1.0;
        while (transaction != 0) {
            transaction = keyb.nextDouble();
            END_BALANCE += transaction;
            if (transaction == 0) {
                return transactions;
            } else {
                if (END_BALANCE < 0) {
                    System.out.println("Insufficient balance, withdrawal denied");
                    END_BALANCE -= transaction;
                } else {
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }
    
    // displayReports(transactions);
    static void displayReports(ArrayList<Double> transactions) {
        int depositCount = 0;
        double depositTotal = 0.0;
        int withdrawalCount = 0;
        double withdrawalTotal = 0.0;
        for (int ndx = 0; ndx < transactions.size(); ndx++) {
            double val = transactions.get(ndx);
            if (val < 0) {
                withdrawalCount++;
                withdrawalTotal += val;
            } else if (val > 0) {
                depositCount++;
                depositTotal += val;
            }
        }
        System.out.println("\nSummary Report:");
        System.out.printf("Opening balance: $%,.2f\n", OPENING_BALANCE);
        System.out.printf("%d deposit(s) totaling $%,.2f\n", depositCount, depositTotal);
        System.out.printf("%d withdrawal(s) totaling $%,.2f\n", withdrawalCount, withdrawalTotal);
        System.out.printf("Ending balance: $%,.2f\n", END_BALANCE);
        
        System.out.println("\nTransaction Amount Details:");
        System.out.println("Transaction Id      Amount      Balance");
        System.out.println("______________      ______      _______");
        Double currentBalance = OPENING_BALANCE;
        for (int ndx = 0; ndx < transactions.size(); ndx++) {
            double val = transactions.get(ndx);
            currentBalance += val;
            System.out.printf("      %2d            %,5.2f      %,7.2f\n",
                         ndx, val, currentBalance);
        }
    }
    
    // displayReports(transactions, "statement.out");
    static void displayReports(ArrayList<Double> transactions, String filename) {
        int depositCount = 0;
        double depositTotal = 0.0;
        int withdrawalCount = 0;
        double withdrawalTotal = 0.0;
        for (int ndx = 0; ndx < transactions.size(); ndx++) {
            double val = transactions.get(ndx);
            if (val < 0) {
                withdrawalCount++;
                withdrawalTotal += val;
            } else if (val > 0) {
                depositCount++;
                depositTotal += val;
            }
        }
        
        try (PrintWriter pw = new PrintWriter(filename)) {
            pw.println("Summary Report:");
            pw.printf("Opening balance: $%,.2f\n", OPENING_BALANCE);
            pw.printf("%d deposit(s) totaling $%,.2f\n", depositCount, depositTotal);
            pw.printf("%d withdrawal(s) totaling $%,.2f\n", withdrawalCount, withdrawalTotal);
            pw.printf("Ending balance: $%,.2f\n", END_BALANCE);
        
            pw.println("\nTransaction Amount Details:");
            pw.println("Transaction Id      Amount      Balance");
            pw.println("______________      ______      _______");
            Double currentBalance = OPENING_BALANCE;
            for (int ndx = 0; ndx < transactions.size(); ndx++) {
                double val = transactions.get(ndx);
                currentBalance += val;
                pw.printf("      %2d            %,5.2f      %,7.2f\n",
                             ndx, val, currentBalance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}