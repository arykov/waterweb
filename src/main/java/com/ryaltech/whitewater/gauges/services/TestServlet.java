package com.ryaltech.whitewater.gauges.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ryaltech.whitewater.gauges.model.RiverInfo;

public class TestServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			JpaWaterWebDao dao=	new JpaWaterWebDao();
			dao.persistRunnableConditions(new RiverInfo());
			RiverInfo ris[] = dao.getAllRiversInfo();
			resp.getWriter().println(ris.length);
		} catch (Exception ex) {
			ex.printStackTrace(resp.getWriter());
		}
	}

}
