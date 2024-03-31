package com.epf.rentmanager.ui.cli.main;

import com.epf.rentmanager.exception.*;

public class main {
    private static cli cli = new cli();
    public static void main(String[] args) throws ServiceException, DaoException {
        cli.menu();
    }
}
