package main;

import controller.CtrlWindow;

import javax.swing.*;
import java.awt.*;

public class Start {

	/**
	 * Starts the application by calling the {@link CtrlWindow}-constructor and showing the corresponding Window.
	 *
	 * @param args
	 * 		the input arguments, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			CtrlWindow ctrlWindow = new CtrlWindow();
			ctrlWindow.showWindow();
		});

	}
}
