package frc.robot.Controllers;

import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
//import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static edu.wpi.first.math.MathUtil.applyDeadband;

public class DriveController extends Controllers {
    XboxController controller;
    public static DriveController driveController;
    public DriveController(int port){
        if (port == -1){
            this.controller = null;
            return;
        }
        this.controller = new XboxController(port);

    }

    public boolean isNull() {
        return this.controller == null;
    }

    public static void createDriveController(){
        driveController = new DriveController(-1);
    }

    public static double deadband(double num){
        return applyDeadband(num, 0.2);
    }
    
    public Trigger povLeft(){
        return new Trigger(() -> controller.getPOV()==270);
    }
    public Trigger povRight(){
        return new Trigger(() -> controller.getPOV()==90);
    }
    public Trigger povUp(){
        return new Trigger(() -> controller.getPOV()==0);
    }
    public Trigger povDown(){
        return new Trigger(() -> controller.getPOV()==180);
    }

    public Trigger getABtn(){
        return new Trigger(() -> controller.getAButton());
    }
    public Trigger getBBtn(){
        return new Trigger(() -> controller.getBButton());
    }
    public Trigger getXBtn(){
        return new Trigger(() -> controller.getXButton());
    }
    public Trigger getYBtn(){
        return new Trigger(() -> controller.getYButton());
    }


    public double getTranslate(){
        return DriveController.deadband(-controller.getLeftY());
    }
    public double getRotation(){
        return DriveController.deadband(-controller.getRightX());
    }
}
