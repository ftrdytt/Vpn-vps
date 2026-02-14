package com.v2ray.ang.ui

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.v2ray.ang.dto.GroupMapItem

/**
 * Pager adapter for subscription groups.
 */
class GroupPagerAdapter(activity: FragmentActivity, var groups: List<GroupMapItem>) : FragmentStateAdapter(activity) {
    
    // نقوم بزيادة العدد 1 لأننا أضفنا صفحة رئيسية في البداية
    override fun getItemCount(): Int = groups.size + 1

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            // إذا كان الترتيب 0 (الأول)، اعرض الصفحة الرئيسية الجديدة
            HomeFragment()
        } else {
            // إذا كان الترتيب 1 أو أكثر، اعرض قائمة السيرفرات (مع تنقيص 1 لأن 0 محجوز)
            GroupServerFragment.newInstance(groups[position - 1].id)
        }
    }

    override fun getItemId(position: Int): Long {
        // نعطي ID ثابت 0 للصفحة الرئيسية
        if (position == 0) return 0L 
        return groups[position - 1].id.hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        if (itemId == 0L) return true
        return groups.any { it.id.hashCode().toLong() == itemId }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(groups: List<GroupMapItem>) {
        this.groups = groups
        notifyDataSetChanged()
    }
}
