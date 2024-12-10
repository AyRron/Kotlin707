//import javafx.application.Application
//import javafx.geometry.Insets
//import javafx.scene.Scene
//import javafx.scene.control.Button
//import javafx.scene.control.Labelimport javax.swing.JButton
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

fun main() {
    // Créer une nouvelle fenêtre
    val frame = JFrame("Swing Example")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(300, 200)

    // Créer un panneau pour contenir les composants
    val panel = JPanel()

    // Créer une étiquette
    val label = JLabel("Hello, Swing!")
    panel.add(label)

    // Créer un bouton
    val button = JButton("Click Me")
    button.addActionListener {
        label.text = "Button Clicked!"
    }
    panel.add(button)

    // Ajouter le panneau à la fenêtre
    frame.contentPane.add(panel)

    // Rendre la fenêtre visible
    frame.isVisible = true
}
//import javafx.scene.control.TextField
//import javafx.scene.layout.VBox
//import javafx.stage.Stage
//
//class CalculatorApp : Application() {
//    override fun start(primaryStage: Stage) {
//        val firstNumberField = TextField()
//        val secondNumberField = TextField()
//        val calculateButton = Button("Calculate")
//        val resultLabel = Label()
//
//        calculateButton.setOnAction {
//            val firstNumber = firstNumberField.text.toDoubleOrNull() ?: 0.0
//            val secondNumber = secondNumberField.text.toDoubleOrNull() ?: 0.0
//            val sum = firstNumber + secondNumber
//            resultLabel.text = "The sum of $firstNumber and $secondNumber is $sum"
//        }
//
//        val vbox = VBox(10.0)
//        vbox.padding = Insets(20.0)
//        vbox.children.addAll(
//            Label("Enter the first number:"),
//            firstNumberField,
//            Label("Enter the second number:"),
//            secondNumberField,
//            calculateButton,
//            resultLabel
//        )
//
//        val scene = Scene(vbox, 300.0, 250.0)
//
//        primaryStage.title = "Calculator App"
//        primaryStage.scene = scene
//        primaryStage.show()
//    }
//}
//
//fun main() {
//    Application.launch(CalculatorApp::class.java)
//}