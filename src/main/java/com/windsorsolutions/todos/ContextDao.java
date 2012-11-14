package com.windsorsolutions.todos;

import java.util.List;

public interface ContextDao {

    public Context persistContext(Context context);

    public void removeContext(Context context);

    public Context mergeContext(Context context);

    public Context getContext(Long id);

    public List getContexts();
}
