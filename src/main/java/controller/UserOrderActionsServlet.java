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

@WebServlet("/user/orders")
public class UserOrderActionsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica se l'utente è loggato
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        String orderIdParam = request.getParameter("orderId");

        if (action == null || orderIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/ordini.jsp?error=invalid_parameters");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            OrderDAO orderDAO = new OrderDAO();

            switch (action) {
                case "receive":
                    orderDAO.doUpdateReceivingStatus(orderId, true);
                    response.sendRedirect(request.getContextPath() + "/ordini.jsp?success=order_received");
                    break;
                case "delete":
                    orderDAO.doDelete(orderId);
                    response.sendRedirect(request.getContextPath() + "/ordini.jsp?success=order_deleted");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/ordini.jsp?error=invalid_action");
                    break;
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/ordini.jsp?error=invalid_order_id");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ordini.jsp?error=update_failed");
        }
    }
}