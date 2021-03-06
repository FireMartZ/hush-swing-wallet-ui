// Copyright (c) 2016-2017 Ivan Vaklinov <ivan@vaklinov.com>
// Copyright (c) 2018 The Hush Developers <contact@myhush.org>
//
// Distributed under the MIT software license, see the accompanying
// file LICENSE or http://www.opensource.org/licenses/mit-license.php.
package org.myhush.gui;

import javax.swing.*;

/**
 * Reporter for periodic errors. Will later have options to filter errors etc.
 */
class StatusUpdateErrorReporter {
    private final JFrame parent;
    private long lastReportedErrorTime = 0;

    StatusUpdateErrorReporter(final JFrame parent) {
        this.parent = parent;
    }

    public void reportError(final Exception e) {
        reportError(e, true);
    }

    public void reportError(final Exception e, final boolean isDueToAutomaticUpdate) {
        e.printStackTrace();

        // TODO: Error logging
        final long time = System.currentTimeMillis();

        // TODO: More complex filtering/tracking in the future
        if (isDueToAutomaticUpdate && (time - lastReportedErrorTime) < (45 * 1000)) {
            return;
        }

        if (isDueToAutomaticUpdate) {
            lastReportedErrorTime = time;
        }

        JOptionPane.showMessageDialog(
                parent,
                "An unexpected error occurred when updating the GUI wallet\n" +
                "state. Please ensure that the HUSH daemon is running.\n\n" + e.getMessage(),
                "Error in updating status.",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
