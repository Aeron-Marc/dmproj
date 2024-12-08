package dm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class dmgame extends JFrame {
    private int playerScore = 0;
    private int computerScore = 0;
    private JLabel scoreLabel;
    private JTextArea resultArea;
    private JLabel playerChoiceLabel;
    private JLabel computerChoiceLabel;

    public dmgame() {
        setTitle("Rock Paper Scissors Game");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Score Panel
        JPanel scorePanel = new JPanel();
        scoreLabel = new JLabel("Player: 0  Computer: 0");
        scorePanel.add(scoreLabel);
        add(scorePanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        String[] choices = {"Rock", "Paper", "Scissors", "Exit"};
        for (String choice : choices) {
            JButton button = new JButton(choice);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        playerChoiceLabel = new JLabel();
        playerChoiceLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0; 
        gbc.weightx = 0.5; 
        imagePanel.add(playerChoiceLabel, gbc);

        JLabel vsLabel = new JLabel("VS");
        vsLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 1;
        gbc.weightx = 0; 
        imagePanel.add(vsLabel, gbc);
        
        gbc.insets = new Insets(0, 20, 0, 0); 
        imagePanel.add(vsLabel, gbc);

        computerChoiceLabel = new JLabel();
        computerChoiceLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 2; 
        gbc.weightx = 0.5; 
        imagePanel.add(computerChoiceLabel, gbc);

        add(imagePanel, BorderLayout.EAST); 

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String playerChoice = e.getActionCommand();

            if (playerChoice.equals("Exit")) {
                JOptionPane.showMessageDialog(null, "Thanks for playing! \nFinal Score \nPlayer: "
                        + playerScore + "\nComputer: " + computerScore);
                System.exit(0);
            } else {
                String computerChoice = getComputerChoice();
                String result = determineWinner(playerChoice, computerChoice);

                if (result.equals("Player wins!")) {
                    playerScore++;
                } else if (result.equals("Computer wins!")) {
                    computerScore++;
                }

                updateResults(playerChoice, computerChoice, result);
                updateImageLabels(playerChoice, computerChoice); 
            }
        }
    }

    private String getComputerChoice() {
        String[] choices = {"Rock", "Paper", "Scissors"};
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equalsIgnoreCase(computerChoice)) {
            return "It's a tie!";
        } else if ((playerChoice.equalsIgnoreCase("Rock") && computerChoice.equalsIgnoreCase("Scissors")) ||
                (playerChoice.equalsIgnoreCase("Scissors") && computerChoice.equalsIgnoreCase("Paper")) ||
                (playerChoice.equalsIgnoreCase("Paper") && computerChoice.equalsIgnoreCase("Rock"))) {
            return "Player wins!";
        } else {
            return "Computer wins!";
        }
    }

    private void updateResults(String playerChoice, String computerChoice, String result) {
        resultArea.append("Player chose: " + playerChoice + "\n");
        resultArea.append("Computer chose: " + computerChoice + "\n");
        resultArea.append(result + "\n");
        scoreLabel.setText("Player: " + playerScore + "  Computer: " + computerScore + "\n");
        resultArea.append("\n");
    }

    private void updateImageLabels(String playerChoice, String computerChoice) {
        playerChoiceLabel.setIcon(new ImageIcon(getClass().getResource("/dm/" + playerChoice.toLowerCase() + ".png")));
        computerChoiceLabel.setIcon(new ImageIcon(getClass().getResource("/dm/" + computerChoice.toLowerCase() + ".png")));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new dmgame().setVisible(true);
            }
        });
    }
}