package com.systex.inet;

import java.io.FileOutputStream;
import java.net.URL;

import com.inet.report.Engine;

public class Main {

    public static void main( String[] args ) {

        try {
            Engine engine = new URLRenderTest().CreateRemoteEngine();
            if(engine == null)
            	System.out.println("engine is null");
            engine.execute();
            java.io.OutputStream pdfFile;
            pdfFile = new FileOutputStream( "E:/sample_engine.pdf" );

            // Request all report pages from the engine
            for( int i = 1; i <= engine.getPageCount(); i++ ) {
                pdfFile.write( engine.getPageData( i ) );
            }
        } catch( Throwable t ) {
            t.printStackTrace();
        }
        System.exit( 0 );
    }

}
