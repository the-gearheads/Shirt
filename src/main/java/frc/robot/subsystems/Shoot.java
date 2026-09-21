package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class Shoot extends SubsystemBase{

    TalonFX shooter = new TalonFX(10);
    TalonFX shooterFollower = new TalonFX(11);
    double output = 0;
    DutyCycleOut shooterOut = new DutyCycleOut(0.0);

    public double getOutput(){
        return output;
    }

    public void setOutput(double num){
        this.output = num;
    }

    public Shoot(){
        configureShoot();
        shooterFollower.setControl(new Follower(shooter.getDeviceID(), MotorAlignmentValue.Opposed));
    }

    @Override
    public void periodic() {
        shooter.setControl(shooterOut.withOutput(-output));
    }

    public void configureShoot() {
        TalonFXConfiguration shooterConfig = new TalonFXConfiguration();
        TalonFXConfiguration shooterFollowerConfig = new TalonFXConfiguration();

        shooterConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        shooterFollowerConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        shooterConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        shooterFollowerConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    }

    public Command shootCommand(double power) {
    return runOnce( () -> this.setOutput(power));
  }
}
