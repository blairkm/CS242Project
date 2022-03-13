package edu.ucr.cs242.project.web;

import edu.ucr.cs242.project.Config;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rehmke
 */
public class GetPhoto extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        // serve logo
        String filepath = Config.IMAGE_ARCHIVE_FILEPATH + System.getProperty("file.separator") + req.getParameter("id");
        File file = new File(filepath);
        res.reset();
        res.setBufferSize(10240);
        res.setContentLength((int) file.length());
        ServletOutputStream fout = res.getOutputStream();
        res.setHeader("Content-Type", "image/png");
        res.setHeader("Content-Length", String.valueOf(file.length()));
        res.setHeader("Content-Disposition", "inline;filename=" + req.getParameter("id") + ".png");

        int length = 0;
        byte[] bbuf = new byte[100];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        while ((in != null) && ((length = in.read(bbuf)) != -1)) {
            fout.write(bbuf, 0, length);
        }
        in.close();
        fout.flush();
        fout.close();

    }

}