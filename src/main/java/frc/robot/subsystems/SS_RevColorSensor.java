package frc.robot.subsystems;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.I2C;
// import edu.wpi.first.wpilibj.command.Subsystem;

// /**
//  * Add your docs here.
//  */
// public class SS_RevColorSensor extends Subsystem {

//   private static final static int COMMAND_REGISTER_BIT = 0x80;

//   private I2C revColor;

//   private

//   enum Color {
//     BLACK, RED, GREEN, BLUE, WHITE
//   }

//   public SS_RevColorSensor(){
//     revColor = new I2C(I2C.Port.kOnboard, 0x39);

//     //power on color sensor
//     revColor.write(COMMAND_REGISTER_BIT | 0x00, 0b00000011);
//   }

//   @Override
//   public void initDefaultCommand() {

//   }

//   public boolean isWhite() {
//     revColor.read(registerAddress, count, buffer);
//     revColor.toString();
//     return true;
//   }

// }


import edu.wpi.first.wpilibj.I2C;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SS_RevColorSensor {
  private static final int CMD = 0x80;
  private static final int MULTI_BYTE_BIT = 0x20;

  private static final int ENABLE_REGISTER  = 0x00;
  private static final int ATIME_REGISTER   = 0x01;
  private static final int PPULSE_REGISTER  = 0x0E;

  private static final int ID_REGISTER     = 0x12;
  private static final int CDATA_REGISTER  = 0x14;
  private static final int RDATA_REGISTER  = 0x16;
  private static final int GDATA_REGISTER  = 0x18;
  private static final int BDATA_REGISTER  = 0x1A;
  private static final int PDATA_REGISTER  = 0x1C;

  private static final int PON   = 0b00000001;
  private static final int AEN   = 0b00000010;
  private static final int PEN   = 0b00000100;
  private static final int WEN   = 0b00001000;
  private static final int AIEN  = 0b00010000;
  private static final int PIEN  = 0b00100000;

  private final double integrationTime = 10;


  private I2C revColor;

  private ByteBuffer buffer = ByteBuffer.allocate(8);

  public short red = 0, green = 0, blue = 0, prox = 0;

  public SS_RevColorSensor(I2C.Port port) {
    buffer.order(ByteOrder.LITTLE_ENDIAN);
      revColor = new I2C(port, 0x39); //0x39 is the address of the color sensor
      
      revColor.write(CMD | 0x00, PON | AEN | PEN);
      
      revColor.write(CMD | 0x01, (int) (256-integrationTime/2.38)); //configures the integration time (time for updating color data)
      revColor.write(CMD | 0x0E, 0b1111);
      
  }

  public void read() {
    buffer.clear();
      revColor.read(CMD | MULTI_BYTE_BIT | RDATA_REGISTER, 8, buffer);
      
      red = buffer.getShort(0);
      if(red < 0) { 
        red += 0b10000000000000000; 
      }
      
      green = buffer.getShort(2);
      if(green < 0) { 
        green += 0b10000000000000000; 
      }
      
      blue = buffer.getShort(4); 
      if(blue < 0) {
         blue += 0b10000000000000000; 
      }
      
      prox = buffer.getShort(6); 
      if(prox < 0) { 
        prox += 0b10000000000000000; 
      }
  }

  public int status() {
    buffer.clear();
    revColor.read(CMD | 0x13, 1, buffer);
    return buffer.get(0);
  }
}