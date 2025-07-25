import LoginPage from "@/app/auth/login/page";
import DashboardLayout from "@/app/dashboard/layout";
import DashboardPage from "@/app/dashboard/page";
import UserDashboardPage from "@/app/dashboard/user/page";
import { Outlet, type RouteObject } from "react-router";

export const routes: RouteObject[] = [
  {
    path: "/",
    element: <div>Home Page</div>,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "*",
    element: <div>Not Found</div>,
  },
  {
    path: "/dashboard",
    element: (
      <DashboardLayout>
        <Outlet />
      </DashboardLayout>
    ),
    children: [
      {
        index: true,
        element: <DashboardPage />,
      },
      {
        path: "/dashboard/user",
        element: <UserDashboardPage />,
      },
      {
        path: "/dashboard/candidates",
        element: (
          <div className="space-y-6">
            <h1 className="text-2xl font-bold text-gray-900">Candidates</h1>
            <p className="text-gray-600">Manage your candidate pipeline</p>
          </div>
        ),
      },
      {
        path: "/dashboard/employees",
        element: (
          <div className="space-y-6">
            <h1 className="text-2xl font-bold text-gray-900">Employees</h1>
            <p className="text-gray-600">View and manage your employees</p>
          </div>
        ),
      },
      {
        path: "/dashboard/job-descriptions",
        element: (
          <div className="space-y-6">
            <h1 className="text-2xl font-bold text-gray-900">
              Job Descriptions
            </h1>
            <p className="text-gray-600">Create and manage job postings</p>
          </div>
        ),
      },
      {
        path: "/dashboard/recruitment",
        element: (
          <div className="space-y-6">
            <h1 className="text-2xl font-bold text-gray-900">Recruitment</h1>
            <p className="text-gray-600">Track recruitment processes</p>
          </div>
        ),
      },
      {
        path: "/dashboard/schedule",
        element: (
          <div className="space-y-6">
            <h1 className="text-2xl font-bold text-gray-900">Schedule</h1>
            <p className="text-gray-600">Manage interviews and meetings</p>
          </div>
        ),
      },
    ],
  },
];
