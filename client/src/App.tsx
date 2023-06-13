import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import Root from './pages/Root';
import QuestionsPage from './pages/QuestionsPage';
import Login from './pages/LoginPage';
import SignUp from './pages/SignUpPage';
import AboutPage from './pages/AboutPage';
import WritePage from './pages/WritePage';
import DetailPage from './pages/DetailPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    errorElement: <QuestionsPage />,
    children: [
      { index: true, element: <QuestionsPage /> },
      {
        path: 'login',
        element: <Login />,
      },
      { path: 'signup', element: <SignUp /> },
      { path: 'detail', element: <DetailPage /> },
      { path: 'write', element: <WritePage /> },
      { path: 'about', element: <AboutPage /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
