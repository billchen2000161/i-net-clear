/*
  i-net software provides programming examples for illustration only, without warranty
  either expressed or implied, including, but not limited to, the implied warranties
  of merchantability and/or fitness for a particular purpose. This programming example
  assumes that you are familiar with the programming language being demonstrated and
  the tools used to create and debug procedures. i-net software support professionals
  can help explain the functionality of a particular procedure, but they will not modify
  these examples to provide added functionality or construct procedures to meet your
  specific needs.
  © i-net software 1998-2015
*/

package rdc;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.inet.report.Engine;
import com.inet.viewer.SwingReportViewer;

/**
 * This class is used by every RDC sample. It provides a Frame for the report viewer and executes the Engine. Every
 * subclass have to create and fill the engine, by implementing the abstract method <code>ceateAndFillEngine</code>. <br>
 * <br>
 * See the source-samples in package rdc for a more information.
 */
public abstract class RDCSample extends JFrame implements EngineCreator {

    /**
     * Initialize the viewer and the GUI for the samples
     */
    protected void initUI() {
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
                e.getWindow().dispose();
                System.exit( 0 );
            }
        } );
        setSize( Toolkit.getDefaultToolkit().getScreenSize() );
        setVisible( true );

        // Create the viewer
        SwingReportViewer viewer = new SwingReportViewer();
        viewer.setHasGroupTree( false );

        try {
            // Set the engine to the viewer
            EngineRDCRenderData proxy = new EngineRDCRenderData( this );
            viewer.addNewReportView( proxy );
            // Add the viewer to the target frame
            getContentPane().add( BorderLayout.CENTER, viewer );

        } catch( Throwable e ) {
            e.printStackTrace();
        }
        setVisible( true );
    }

    /**
     * Abstract method to create and fill the engine for a report.
     * @param exportFmt output format
     * @return engine the engine for the specified report
     */
    @Override
    public abstract Engine createAndFillEngine( String exportFmt );
}
