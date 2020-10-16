/********************************************************************************/
/*                                                                              */
/*              Ball.java                                                       */
/*                                                                              */
/*      description of class                                                    */
/*                                                                              */
/*      Written by eiseleappes                                                  */
/*                                                                              */
/********************************************************************************/



import java.awt.Color;



class Ball
{

       private double posX, posY, radius, velX, velY;
       private Color color;
       
       public Ball(double posX, double posY, double radius, double velX, double velY, Color color) {
           this.posX = posX;
           this.posY = posY;
           this.radius = radius;
           this.velX = velX;
           this.velY = velY;
           this.color = color;
       }
       
       double getPosX() {
           return posX;
       }
       
       double getPosY() {
           return posY;
       }
       
       double getRadius() {
           return radius;
       }
       
       double getVelX() {
           return velX;
       }
       
       double getVelY() {
           return velY;
       }
       
       Color getColor() {
           return color;
       }
       
       void setPosX(double posX) {
           this.posX = posX;
       }
       
       void setPosY(double posY) {
           this.posY = posY;
       }
       
       void setVelX(double velX) {
           this.velX = velX;
       }
       
       void setVelY(double velY) {
           this.velY = velY;
       }
       
       void setRadius(double radius) { 
           this.radius = radius;
       }
       
       void setColor(Color color) {
           this.color = color;
       }
   
}




/* end of Ball.java */
