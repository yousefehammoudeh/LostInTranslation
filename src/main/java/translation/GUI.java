package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        SwingUtilities.invokeLater(() -> {
            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            JPanel countryPanel = new JPanel();
            String[] countries = new String[translator.getCountryCodes().size()];
            int i = 0;
            for (String countryCode : translator.getCountryCodes()) {
                countries[i++] = countryCodeConverter.fromCountryCode(countryCode);
            }
            JList<String> countryList = new JList<>(countries);
            JScrollPane scrollPane = new JScrollPane(countryList);
            countryPanel.add(scrollPane);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            resultPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultLabel);

            languageComboBox.addItemListener(new ItemListener() {

                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryList.getSelectedValue().toString();

                    Translator translator = new JSONTranslator();

                    String result = translator.translate(countryCodeConverter.fromCountry(country).toLowerCase(),
                            languageCodeConverter.fromLanguage(language).toLowerCase());
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryList.getSelectedValue().toString();

                    Translator translator = new JSONTranslator();

                    String result = translator.translate(countryCodeConverter.fromCountry(country).toLowerCase(),
                            languageCodeConverter.fromLanguage(language).toLowerCase());
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
