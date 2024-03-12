package org.example.services;

import java.util.List;

public interface PatternFoundCallbackService {
    void onPatternFound(int[] indexes, int pageIndex);

    void onTaskComplete(boolean result);
}
