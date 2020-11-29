package org.techtown.ppackchinda.ui.hof;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import org.techtown.ppackchinda.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HOFFragment extends ListFragment {
    int priority[] = new int[10];
    String name[] = new String[10];
    long time[] = new long[10];
    TextView t,p;
    ListView l;

    SimpleDateFormat fromat = new SimpleDateFormat("HH:nn:ss");
    ArrayList<ListViewItemhof> itemList = new ArrayList<ListViewItemhof>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_hof, container, false);
        p = (TextView)v.findViewById(R.id.priority);
        l = (ListView)v.findViewById(android.R.id.list);
        t = (TextView)v.findViewById(R.id.hoftext);
        for(int i=0;i<10;i++)
            priority[i]=i+1;
        l.setVisibility(View.VISIBLE);
        t.setVisibility(View.INVISIBLE);
        ListViewAdapterhof adapter = new ListViewAdapterhof(itemList);
        l.setAdapter(adapter);
        //DB에서 가져와 아이템 추가
        adapter.addhof(0,"김",System.currentTimeMillis());
        adapter.addhof(0,"이",System.currentTimeMillis());
        if(adapter.getCount() == 0)
        {
            t.setVisibility(View.VISIBLE);
            l.setVisibility(View.INVISIBLE);
        }
        Comparator<ListViewItemhof> noAsc = new Comparator<ListViewItemhof>() {
            @Override
            public int compare(ListViewItemhof item1, ListViewItemhof item2) {
                int ret ;

                if (item1.getDesc()< item2.getDesc())
                    ret = -1 ;
                else if (item1.getDesc() ==item1.getDesc())
                    ret = 0 ;
                else
                    ret = 1 ;

                return ret ;

                // 위의 코드를 간단히 만드는 방법.
                // return (item1.getNo() - item2.getNo()) ;
            }
        } ;

        Collections.sort(itemList, noAsc) ;
        for(int i=0;i<adapter.getCount();i++)
        {
            adapter.listViewItemList.get(i).setNo(i+1);
        }
        adapter.notifyDataSetChanged() ;
        return v;

    }
    class ListViewItemhof{
        private int no;
        private String titleStr ;
        private long descStr;
        public void setTitle(String title) {
            titleStr = title ;
        }
        public void setNo(int no) {
            this.no = no ;
        }
        public int getNo() {
            return no ;
        }
        public void setDesc(long desc) {
            descStr = desc ;
        }

        public String getTitle() {
            return this.titleStr ;
        }
        public long getDesc() {
            return this.descStr ;
        }
    }
    public class ListViewAdapterhof extends BaseAdapter {
        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<ListViewItemhof> listViewItemList ;

        // ListViewAdapter의 생성자
        public ListViewAdapterhof( ArrayList<ListViewItemhof> itemhofs) {
            if(itemhofs == null)
                listViewItemList = new ArrayList<ListViewItemhof>() ;
            else {
            listViewItemList = itemhofs ;
        }}

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return listViewItemList.size() ;
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_hof, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            TextView priorityView = (TextView) convertView.findViewById(R.id.priority);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
            TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            org.techtown.ppackchinda.ui.hof.HOFFragment.ListViewItemhof listViewItem = listViewItemList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            priorityView.setText(Integer.toString(listViewItem.getNo()));
            titleTextView.setText(listViewItem.getTitle());
            descTextView.setText(Long.toString(listViewItem.getDesc()));

            return convertView;
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position ;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return listViewItemList.get(position) ;
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addhof( int no,String title, long desc) {
            ListViewItemhof item = new ListViewItemhof();
            //만약 사용자 시간이 10등 시간보다 크면 아무것도 안함.
            //10등 이하일경우 1등부터 비교해가며 중간에 해당 순위 정보에 insert하여 갱신.
            item.setNo(no);
            item.setTitle(title);
            item.setDesc(desc);

            listViewItemList.add(item);
        }

    }
    }

