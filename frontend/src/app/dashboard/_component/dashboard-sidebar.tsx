"use client";

import * as React from "react";
import {
  LayoutDashboard,
  Users,
  UserCheck,
  FileText,
  Briefcase,
  Calendar,
  Settings,
  LogOut,
  Building2,
} from "lucide-react";
import { Link, useLocation } from "react-router";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar";

// Dashboard-specific data structure
const data = {
  teams: [
    {
      name: "EmpDex Inc",
      logo: Building2,
      plan: "Enterprise",
    },
    {
      name: "HR Department",
      logo: Users,
      plan: "Professional",
    },
    {
      name: "Recruitment Team",
      logo: UserCheck,
      plan: "Standard",
    },
  ],
  navMain: [
    {
      title: "Dashboard",
      url: "/dashboard",
      icon: LayoutDashboard,
      isActive: false,
    },
    {
      title: "Candidates",
      url: "/dashboard/candidates",
      icon: Users,
    },
    {
      title: "Employees",
      url: "/dashboard/employees",
      icon: UserCheck,
    },
    {
      title: "Job Descriptions",
      url: "/dashboard/job-descriptions",
      icon: FileText,
    },
    {
      title: "Recruitment",
      url: "/dashboard/recruitment",
      icon: Briefcase,
    },
    {
      title: "Schedule",
      url: "/dashboard/schedule",
      icon: Calendar,
    },
  ],
};

// Team Switcher Component
function TeamSwitcher({ teams }: { teams: typeof data.teams }) {
  const [selectedTeam, setSelectedTeam] = React.useState(teams[0]);

  return (
    <div className="flex items-center gap-2 px-4">
      <div className="flex items-center gap-2">
        <selectedTeam.logo className="h-6 w-6" />
        <div className="flex flex-col">
          <span className="text-sm font-semibold">{selectedTeam.name}</span>
          <span className="text-xs text-muted-foreground">
            {selectedTeam.plan}
          </span>
        </div>
      </div>
    </div>
  );
}

// Main Navigation Component
function NavMain({ items }: { items: typeof data.navMain }) {
  const location = useLocation();

  return (
    <nav className="space-y-1">
      {items.map((item) => {
        const Icon = item.icon;
        const isActive = location.pathname === item.url;

        return (
          <Link
            key={item.title}
            to={item.url}
            className={`flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors ${
              isActive
                ? "bg-accent text-accent-foreground"
                : "text-muted-foreground hover:bg-accent hover:text-accent-foreground"
            }`}
          >
            <Icon className="h-4 w-4" />
            <span>{item.title}</span>
          </Link>
        );
      })}
    </nav>
  );
}

// User Navigation Component
function NavUser() {
  return (
    <div className="space-y-1">
      <button className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-muted-foreground hover:bg-accent hover:text-accent-foreground">
        <Settings className="h-4 w-4" />
        <span>Settings</span>
      </button>
      <button className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-muted-foreground hover:bg-accent hover:text-accent-foreground">
        <LogOut className="h-4 w-4" />
        <span>Logout</span>
      </button>
    </div>
  );
}

export default function DashboardSidebar({
  ...props
}: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
