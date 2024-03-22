import java.awt.*;

//ball
public class Ball {
    double x;
    double y;
    double size;
    double velocityX;
    double velocityY;

    Color color = Color.PINK;

    public Ball(double x, double y, double velocityX, double velocityY, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.color = color;
    }
}
