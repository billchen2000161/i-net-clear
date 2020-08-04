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

package com.systex.inet;

import java.io.FileOutputStream;
import java.io.IOException;

import com.inet.report.Engine;
import com.inet.report.Listener;
import com.inet.report.ReportException;
import com.inet.report.output.DocumentOutputStream;
import com.inet.viewer.SwingViewerContext;
import com.inet.viewer.URLRenderData;

/**
 * This sample shows you how you can export an report without the preview in the viewer. An export dialog will pop up
 * and then the report will be exported to the file you choose.
 */
public class ExportWithoutPreview {

    private static Listener LISTENER;

    private URLRenderData   renderConnection; // This is our main render data connection - the source of our raw report data coming from our report server

    /**
     * Exports an report without the preview in the viewer.
     */
    public ExportWithoutPreview() {
        // we initialize the render data connection (assuming the URL is correct)
        renderConnection = new URLRenderData("http://10.10.56.198:1500/?reports=C:\\ProgramData\\i-net software\\reporting_System_Default\\samplereports\\Issue.rpt" );
        // If you use your own i-net Clear Reports server then use the report URL for this server, e.g.:
        // renderConnection.setReportURL ( "http://serverName:9000/?report=file:c:/java/sample.rpt" ); 

        SwingViewerContext swingViewerContext = new SwingViewerContext( null );

        // export report to desired file
        swingViewerContext.export( null, renderConnection );
    }

    /**
     * Main method of this sample
     * @param args arguments not used
     * @throws IOException in case of IO error (e.g. port already in use)
     * @throws ReportException 
     */
    public static void main( String[] args ) throws IOException, ReportException {
    	 Engine eng = new Engine( Engine.EXPORT_PDF );
    	 eng.setReportFile( "E:/recall.rpt" );
    	 eng.setDocumentOutput(new DocumentOutputStream( new FileOutputStream( "E:/recall.pdf" ), true ));
    	 eng.execute();
    	 System.out.println("done");
    }
}
