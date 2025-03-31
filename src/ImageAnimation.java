import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageAnimation extends JPanel implements ActionListener {
    private Image image;
    private int x, y;
    private Timer timer;

    public ImageAnimation() {
        // Инициализируем начальные координаты
        x = 0;
        y = 0;

        // Загружаем изображение через диалоговое окно выбора файла
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите изображение");
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            image = new ImageIcon(selectedFile.getAbsolutePath()).getImage();
        } else {
            System.out.println("Изображение не выбрано. Программа будет завершена.");
            System.exit(0);
        }

        // Устанавливаем белый фон
        setBackground(Color.WHITE);

        // Создаем таймер для анимации
        timer = new Timer(30, this); // обновление каждый 30 миллисекунд
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Рисуем изображение
        g.drawImage(image, x, y, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Обновляем координаты
        x += 5;
        y += 5;

        // Проверяем, вышло ли изображение за пределы панели
        if (x > getWidth() || y > getHeight()) {
            x = 0;  // Сбрасываем координаты
            y = 0;
        }

        // Перерисовываем панель
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Animation");
        ImageAnimation animationPanel = new ImageAnimation();

        frame.add(animationPanel);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
