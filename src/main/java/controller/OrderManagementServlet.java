package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.beans.User;
import model.dao.OrderDAO;

import java.io.IOException;

@WebServlet("/admin/orders")
public class OrderManagementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica se l'utente è admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=access_denied");
            return;
        }

        String action = request.getParameter("action");
        String orderIdParam = request.getParameter("orderId");

        if (action == null || orderIdParam == null) {
            request.setAttribute("error", "invalid_parameters");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            OrderDAO orderDAO = new OrderDAO();

            if ("ship".equals(action)) {
                orderDAO.doUpdateShippingStatus(orderId, true);
                request.setAttribute("success", "order_shipped");
                request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "invalid_order_id");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "update_failed");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        }
    }
}