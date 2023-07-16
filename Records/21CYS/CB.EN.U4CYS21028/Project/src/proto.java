import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

/*
This is an e-voting application program using Java Swing
@Author: Anuvarshini M K , Hitesh Manjunath K , Suhitha K
*/

public class proto extends JFrame {

    private Map<String, String> registeredVoters; // Store registered voters (voterID -> password)
    private List<String> candidates; // Store the list of candidates
    private Map<String, Integer> voteCounts; // Store vote counts for each candidate
    private Set<String> votedVoters; // Store the IDs of voters who have already voted

    private JTabbedPane tabbedPane;
    private JTextField voterIDFieldReg;
    private JPasswordField passwordFieldReg;
    private JButton registerButton;
    private JTextField voterIDFieldVote;
    private JPasswordField passwordFieldVote;
    private JComboBox<String> candidateComboBox;
    private JButton voteButton;
    private JButton countVotesButton;

    public proto() {
        registeredVoters = new HashMap<>();
        candidates = new ArrayList<>();
        voteCounts = new HashMap<>();
        votedVoters = new HashSet<>();

        // Initialize Swing components
        voterIDFieldReg = new JTextField(20);
        passwordFieldReg = new JPasswordField(20);
        registerButton = new JButton("Register");
        voterIDFieldVote = new JTextField(20);
        passwordFieldVote = new JPasswordField(20);
        candidateComboBox = new JComboBox<>();
        voteButton = new JButton("Vote");
        countVotesButton = new JButton("Count Votes");

        // Set layout
        setLayout(new GridLayout(1, 1));

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Create registration tab
        JPanel registrationPanel = new JPanel(new FlowLayout());
        registrationPanel.add(new JLabel("Voter ID:"));
        registrationPanel.add(voterIDFieldReg);
        registrationPanel.add(new JLabel("Password:"));
        registrationPanel.add(passwordFieldReg);
        registrationPanel.add(registerButton);
        tabbedPane.addTab("Registration", registrationPanel);

        // Create voting tab
        JPanel votingPanel = new JPanel(new FlowLayout());
        votingPanel.add(new JLabel("Voter ID:"));
        votingPanel.add(voterIDFieldVote);
        votingPanel.add(new JLabel("Password:"));
        votingPanel.add(passwordFieldVote);
        votingPanel.add(new JLabel("Select Candidate:"));
        votingPanel.add(candidateComboBox);
        votingPanel.add(voteButton);
        tabbedPane.addTab("Voting", votingPanel);

        // Create results tab
        JPanel resultsPanel = new JPanel(new FlowLayout());
        resultsPanel.add(countVotesButton);
        tabbedPane.addTab("Results", resultsPanel);

        // Add tabbed pane to the frame
        add(tabbedPane);

        // Add action listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String voterID = voterIDFieldReg.getText();
                String password = new String(passwordFieldReg.getPassword());

                if (registeredVoters.containsKey(voterID)) {
                    JOptionPane.showMessageDialog(proto.this, "Voter ID already exists. Please choose a different Voter ID.");
                } else {
                    registeredVoters.put(voterID, password);
                    JOptionPane.showMessageDialog(proto.this, "Registration successful!");
                    tabbedPane.setSelectedIndex(1); // Switch to the Voting tab after registration
                }
            }
        });

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String voterID = voterIDFieldVote.getText();
                String password = new String(passwordFieldVote.getPassword());

                if (!registeredVoters.containsKey(voterID)) {
                    int option = JOptionPane.showConfirmDialog(proto.this, "Voter ID not found. Do you want to register?", "Voter ID Not Found", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        tabbedPane.setSelectedIndex(0); // Switch to the Registration tab
                    }
                } else if (votedVoters.contains(voterID)) {
                    JOptionPane.showMessageDialog(proto.this, "You have already cast your vote.");
                } else {
                    String selectedCandidate = (String) candidateComboBox.getSelectedItem();
                    vote(voterID, selectedCandidate);
                }
            }
        });

        countVotesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countVotes();
            }
        });
    }

    private void vote(String voterID, String candidate) {
        if (!voteCounts.containsKey(candidate)) {
            voteCounts.put(candidate, 1);
        } else {
            int count = voteCounts.get(candidate);
            voteCounts.put(candidate, count + 1);
        }

        votedVoters.add(voterID);

        JOptionPane.showMessageDialog(this, "Vote cast successfully!");
        candidateComboBox.setSelectedIndex(0);
    }

    private void countVotes() {
        StringBuilder result = new StringBuilder("Vote Counts:\n");
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(this, result.toString());

        // Save vote counts to file
        saveVoteCountsToFile();
    }

    private void saveVoteCountsToFile() {
        Path filePath = Path.of("vote.txt");
        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }

            StringBuilder fileContent = new StringBuilder();
            for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
                fileContent.append(entry.getKey()).append(",").append(entry.getValue()).append(System.lineSeparator());
            }

            Files.write(filePath, fileContent.toString().getBytes(), StandardOpenOption.APPEND);
            JOptionPane.showMessageDialog(this, "Vote counts saved to file successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving vote counts to file: " + e.getMessage());
        }
    }

    public void addCandidateName(String candidateName) {
        candidates.add(candidateName);
        candidateComboBox.addItem(candidateName);
    }

    public static void main(String[] args) {
        proto application = new proto();

        // Prompt the user to enter the number of candidates
        int numCandidates = Integer.parseInt(JOptionPane.showInputDialog(application, "Enter the number of candidates:"));
        for (int i = 1; i <= numCandidates; i++) {
            String candidateName = JOptionPane.showInputDialog(application, "Enter candidate name " + i + ":");
            application.addCandidateName(candidateName);
        }

        // Prompt the user if they want to register or vote
        String[] options = {"Register", "Vote"};
        int choice = JOptionPane.showOptionDialog(application, "Do you want to register or vote?", "Registration or Voting", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            application.showRegistrationTab();
        } else {
            application.showVotingTab();
        }

        application.setSize(400, 200);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setVisible(true);
    }

    private void showRegistrationTab() {
        tabbedPane.setSelectedIndex(0); // Switch to the Registration tab
    }

    private void showVotingTab() {
        tabbedPane.setSelectedIndex(1); // Switch to the Voting tab
    }
}